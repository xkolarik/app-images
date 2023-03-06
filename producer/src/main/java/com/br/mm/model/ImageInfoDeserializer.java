package com.br.mm.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class ImageInfoDeserializer implements Deserializer<ImageInfo> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ImageInfo deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, ImageInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}