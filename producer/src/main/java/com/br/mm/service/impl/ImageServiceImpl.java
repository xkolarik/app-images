package com.br.mm.service.impl;

import com.br.mm.model.ImageInfo;
import com.br.mm.service.ImageService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.*;
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE);
    private static final String IMAGE_UPLOAD_TOPIC = "imagekafka";
    private MinioClient minioClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String accessKey = "minio";
    private static final String secretKey = "minio123";
    private static final String url = "http://localhost:9000";
    @Value("${minio.bucket.name}")
    private String minioBucketName;
    @Autowired
    public ImageServiceImpl(MinioClient minioClient, KafkaTemplate<String, Object> kafkaTemplate) {
        this.minioClient = minioClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile image, String fileName, String contentType) {
        if (image.isEmpty() || StringUtils.isEmpty(fileName) || StringUtils.isEmpty(contentType)) {
            return new ResponseEntity<>("Image, file name, and content type are required", HttpStatus.BAD_REQUEST);
        }

        if (!ALLOWED_IMAGE_TYPES.contains(image.getContentType())) {
            return new ResponseEntity<>("Unsupported image type. Allowed types are: " + ALLOWED_IMAGE_TYPES, HttpStatus.BAD_REQUEST);
        }

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioBucketName)
                            .object(fileName)
                            .contentType(contentType)
                            .stream(new ByteArrayInputStream(image.getBytes()), image.getSize(), -1)
                            .build()
            );

            String url = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                            .bucket(minioBucketName)
                            .object(fileName)
                            .method(Method.GET)
                            .build()
            );

            ImageInfo imageInfo = new ImageInfo(fileName, contentType, url);
            kafkaTemplate.send(IMAGE_UPLOAD_TOPIC, imageInfo);

            return new ResponseEntity<>("Image successfully uploaded", HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while uploading the image", e);
            return new ResponseEntity<>("An error occurred while uploading the image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
