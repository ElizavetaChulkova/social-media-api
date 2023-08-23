package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chulkova.socialmediaapi.dto.ProfileDto;
import ru.chulkova.socialmediaapi.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService service;

    @GetMapping
    public List<ProfileDto> getAll() {
        log.info("get all users");
        return service.getAll();
    }
}