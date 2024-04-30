package com.owl.fitness_service.handlers;


import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


@Component("imageHandler")
public class ImageHandler {
    @SneakyThrows
    public Mono<ServerResponse> getImgByPath(ServerRequest request) {
        String dir = "D:\\Owl\\java_applications\\fitness-service\\back\\images\\";

        String relativePath = request.pathVariable("relativePath");

        String[] pathStrings = relativePath.split("\\|");

        String imgPath = pathStrings[0] + File.separator + pathStrings[1];

        return ServerResponse.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(Mono.just(Files.readAllBytes(Path.of(dir + imgPath))), byte[].class);
    }
}