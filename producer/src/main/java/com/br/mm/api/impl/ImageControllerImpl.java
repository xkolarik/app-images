package com.br.mm.api.impl;

import com.br.mm.api.spec.ImageController;

import com.br.mm.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageControllerImpl implements ImageController {

    @Autowired
    private ImageService imageService;

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile image, String fileName, String contentType) {
        return imageService.uploadImage(image, fileName, contentType);
    }
}
