import com.br.mm.model.ImageInfo;
import com.br.mm.service.impl.ImageServiceImpl;

import io.minio.MinioClient;

import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import org.springframework.mock.web.MockMultipartFile;


@ExtendWith(MockitoExtension.class)
public class ImageTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private KafkaTemplate<String, ImageInfo> kafkaTemplate;

    @InjectMocks
    private ImageServiceImpl imageServiceImpl;

    private String  minioBucketName = "images";

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE);


    @Test
    public void testUploadImageWithEmptyImage() {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.isEmpty()).thenReturn(true);

        ResponseEntity<String> response = imageServiceImpl.uploadImage(mockMultipartFile, "test-name", "image/jpeg");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Image, file name, and content type are required");
    }

    @Test
    public void testUploadImageWithEmptyFileName() {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.isEmpty()).thenReturn(false);

        ResponseEntity<String> response = imageServiceImpl.uploadImage(mockMultipartFile, "", "image/jpeg");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Image, file name, and content type are required");
    }

    @Test
    public void testUploadImageWithEmptyContentType() {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.isEmpty()).thenReturn(false);

        ResponseEntity<String> response = imageServiceImpl.uploadImage(mockMultipartFile, "test-name", "");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Image, file name, and content type are required");
    }

    @Test
    public void testUploadImageWithUnsupportedContentType() {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.isEmpty()).thenReturn(false);
        when(mockMultipartFile.getContentType()).thenReturn("unsupported/type");

        ResponseEntity<String> response = imageServiceImpl.uploadImage(mockMultipartFile, "test-name", "unsupported/type");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Unsupported image type. Allowed types are: " + ALLOWED_IMAGE_TYPES);
    }

    @Test
    public void testImageUploadWithEmptyImage() {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.isEmpty()).thenReturn(true);
        ResponseEntity<String> response = imageServiceImpl.uploadImage(mockMultipartFile, "test-name", "image/jpeg");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Image, file name, and content type are required");
        verifyNoInteractions(minioClient);
        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    public void testImageUploadWithEmptyFileName() {
        MultipartFile mockMultipartFile = mock(MultipartFile.class);
        when(mockMultipartFile.isEmpty()).thenReturn(false);
        ResponseEntity<String> response = imageServiceImpl.uploadImage(mockMultipartFile, "", "image/jpeg");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Image, file name, and content type are required");
    }

    @Test
    public void testUploadImage() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        byte[] imageBytes = {1, 2, 3};
        MockMultipartFile image = new MockMultipartFile("image", "test.png", IMAGE_PNG_VALUE, imageBytes);
        String fileName = "test.png";
        String contentType = IMAGE_PNG_VALUE;

        ReflectionTestUtils.setField(imageServiceImpl,"minioBucketName", minioBucketName);

        when(minioClient.getPresignedObjectUrl(any()))
                .thenReturn("https://minio.test/test.png");

        ResponseEntity<String> response = imageServiceImpl.uploadImage(image, fileName, contentType);

        verify(minioClient, times(1))
                .putObject(any());

        verify(minioClient, times(1))
                .getPresignedObjectUrl(any());

        verify(kafkaTemplate, times(1))
                .send(eq("imagekafka"), any(ImageInfo.class));

        assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody().equals("Image successfully uploaded"));
    }
}
