package com.br.mm.consumer;

import com.br.mm.model.ImageInfo;
import com.br.mm.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @Autowired
    private ImageService imageService;

    @KafkaListener(topics = "imagekafka", groupId = "group-id")
    public void consume(ImageInfo imageInfo) {
        imageService.save(imageInfo);
    }
}
