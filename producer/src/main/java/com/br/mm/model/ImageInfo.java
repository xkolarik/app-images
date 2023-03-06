package com.br.mm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo extends StringSerializer {

    private String fileName;
    private String contentType;
    private String url;

}
