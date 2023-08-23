package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.chulkova.socialmediaapi.dto.PostDto;
import ru.chulkova.socialmediaapi.mapper.PostMapper;
import ru.chulkova.socialmediaapi.model.Image;
import ru.chulkova.socialmediaapi.model.Post;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.ImageRepository;
import ru.chulkova.socialmediaapi.repository.PostRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository repository;
    private final ImageRepository imageRepository;

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> getPosts(@AuthenticationPrincipal User user) {
        log.info("get all user posts");
        return repository.findAllByUserId(user.id())
                .stream().map(PostMapper::getTo).collect(Collectors.toList());
    }

    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> create(@AuthenticationPrincipal User user,
                                          @RequestParam("title") String title,
                                          @RequestParam("text") String text,
                                          @RequestPart("image") MultipartFile multipartImage) throws IOException {
        log.info("create new post with image");
        Image image = Image.builder()
                .name(multipartImage.getName())
                .content(multipartImage.getBytes())
                .build();
        imageRepository.save(image);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/image/download/")
                .path(String.valueOf(image.id()))
                .toUriString();
        Post created = Post.builder()
                .title(title)
                .dateTime(LocalDateTime.now())
                .text(text)
                .user(user)
                .image(fileDownloadUri)
                .build();
        repository.save(created);
        return ResponseEntity.ok(PostMapper.getTo(created));
    }

    @PutMapping(value = "/post/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> update(@PathVariable("postId") Long postId,
                                          @RequestParam("title") String title,
                                          @RequestParam("text") String text,
                                          @RequestPart("image") MultipartFile multipartImage) throws IOException {
        log.info("update new post with image");
        Image toUpdate = imageRepository.findByName(multipartImage.getName()).orElse(null);
        assert toUpdate != null;
        toUpdate.setName(multipartImage.getName());
        toUpdate.setContent(multipartImage.getBytes());
        imageRepository.save(toUpdate);
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/image/download/")
                .path(String.valueOf(toUpdate.id()))
                .toUriString();
        Post post = repository.findById(postId).orElseThrow();
        post.setTitle(title);
        post.setText(text);
        post.setImage(fileDownloadUri);
        post.setDateTime(LocalDateTime.now());
        Post updated = repository.save(post);
        return ResponseEntity.ok(PostMapper.getTo(updated));
    }

    @DeleteMapping(value = "/post/{postId}")
    public void delete(@PathVariable("postId") Long postId) {
        log.info("delete post");
        repository.deleteById(postId);
    }
}