package com.br.mm.api.spec;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "API de Upload de Imagens")
public interface ImageController {

    @ApiOperation(value = "Uploda de uma imagem", httpMethod = "POST")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successful request"),
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 400, message = "Your request has invalid information or structure"),
            @ApiResponse(code = 422, message = "An business error happened")})
    @PostMapping(path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image,
                                       @RequestParam("fileName") String fileName,
                                       @RequestParam("contentType") String contentType);
}
