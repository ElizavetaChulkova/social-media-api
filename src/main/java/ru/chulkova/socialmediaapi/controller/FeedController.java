package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chulkova.socialmediaapi.dto.PostDto;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.service.FeedService;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class FeedController {

    private final FeedService service;

    @GetMapping("/feed")
    public List<PostDto> getFeed(@AuthenticationPrincipal User user) {
        log.info("get friends feed");
        return service.getFeed(user);
    }
}