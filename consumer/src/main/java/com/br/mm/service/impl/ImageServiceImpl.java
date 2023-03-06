package com.br.mm.service.impl;

import com.br.mm.model.ImageInfo;
import com.br.mm.repository.ImageRepository;
import com.br.mm.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    @Override
    public void save(ImageInfo imageInfo) {
        ImageInfo response = imageRepository.insert(imageInfo);
        log.info("Imagen Inserida com sucesso" + response);
    }
}
