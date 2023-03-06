package com.br.mm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("TBL_Dados_Image")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageInfo {

    @Id
    private String id;
    private String fileName;
    private String contentType;
    private String url;

}
