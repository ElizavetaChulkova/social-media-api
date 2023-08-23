package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chulkova.socialmediaapi.dto.PostDto;
import ru.chulkova.socialmediaapi.mapper.PostMapper;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final PostRepository postRepository;

    @GetMapping("/feed")
    public List<PostDto> getFeed(@AuthenticationPrincipal User user) {
        log.info("get friends feed");
        return postRepository.getPostsByFriends(user.id())
                .stream().map(u -> PostMapper.getTo(u))
                .collect(Collectors.toList());
    }
}