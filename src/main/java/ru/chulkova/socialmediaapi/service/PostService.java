package ru.chulkova.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    public List<PostDto> getPosts(User user) {
        return postRepository.findAllByUserId(user.id())
                .stream().map(PostMapper::getTo).collect(Collectors.toList());
    }

    public PostDto createPost(User user, String title, String text, String uri) {
        Post created = Post.builder()
                .title(title)
                .dateTime(LocalDateTime.now())
                .text(text)
                .user(user)
                .image(uri)
                .build();
        postRepository.save(created);
        return PostMapper.getTo(created);
    }

    public Image uploadImage(MultipartFile multipartImage) throws IOException {
        Image image = Image.builder()
                .name(multipartImage.getName())
                .content(multipartImage.getBytes())
                .build();
        imageRepository.save(image);
        return image;
    }

    public String imageDownloadUri(Image image) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/image/download/")
                .path(String.valueOf(image.id()))
                .toUriString();
    }

    public Image updateImage(MultipartFile multipartImage) throws IOException {
        Image toUpdate = imageRepository.findByName(multipartImage.getName())
                .orElse(null);
        assert toUpdate != null;
        toUpdate.setName(multipartImage.getName());
        toUpdate.setContent(multipartImage.getBytes());
        imageRepository.save(toUpdate);
        return toUpdate;
    }

    public PostDto updatePost(Long postId, String title, String text, String uri) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.setTitle(title);
        post.setText(text);
        post.setImage(uri);
        post.setDateTime(LocalDateTime.now());
        Post updated = postRepository.save(post);
        return PostMapper.getTo(updated);
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}