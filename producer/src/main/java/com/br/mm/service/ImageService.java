package com.br.mm.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ResponseEntity<String> uploadImage(MultipartFile image, String fileName, String contentType);
}
