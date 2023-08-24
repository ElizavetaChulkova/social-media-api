package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.chulkova.socialmediaapi.exception.NotFoundException;
import ru.chulkova.socialmediaapi.repository.ImageRepository;
import ru.chulkova.socialmediaapi.service.PostService;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageRepository imageRepository;
    private final PostService service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long uploadImage(@RequestParam(value = "image") MultipartFile multipartImage)
            throws IOException {
        return service.uploadImage(multipartImage).id();
    }

    @GetMapping(value = "/download/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource downloadImage(@PathVariable Long imageId) {
        byte[] image = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Image not found"))
                .getContent();
        return new ByteArrayResource(image);
    }
}