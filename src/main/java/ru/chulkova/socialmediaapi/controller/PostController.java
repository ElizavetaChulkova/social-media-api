package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.chulkova.socialmediaapi.dto.PostDto;
import ru.chulkova.socialmediaapi.model.Image;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.service.PostService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> getPosts(@AuthenticationPrincipal User user) {
        log.info("get all user posts");
        return service.getPosts(user);
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> create(@AuthenticationPrincipal User user,
                                          @RequestParam("title") String title,
                                          @RequestParam("text") String text,
                                          @RequestPart("image") MultipartFile multipartImage)
            throws IOException {
        log.info("create new post with image");
        Image image = service.uploadImage(multipartImage);
        String uri = service.imageDownloadUri(image);
        PostDto created = service.createPost(user, title, text, uri);
        return ResponseEntity.ok(created);
    }

    @PutMapping(value = "/post/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> update(@PathVariable("postId") Long postId,
                                          @RequestParam("title") String title,
                                          @RequestParam("text") String text,
                                          @RequestPart("image") MultipartFile multipartImage)
            throws IOException {
        log.info("update new post with image");
        Image image = service.updateImage(multipartImage);
        String uri = service.imageDownloadUri(image);
        PostDto updated = service.updatePost(postId, title, text, uri);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(value = "/post/{postId}")
    public void delete(@PathVariable("postId") Long postId) {
        log.info("delete post");
        service.delete(postId);
    }
}