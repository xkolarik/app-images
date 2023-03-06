package com.br.mm.repository;

import com.br.mm.model.ImageInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ImageRepository extends MongoRepository<ImageInfo, String> {
}
