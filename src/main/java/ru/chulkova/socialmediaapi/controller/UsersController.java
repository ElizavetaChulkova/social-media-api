package ru.chulkova.socialmediaapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chulkova.socialmediaapi.dto.ProfileDto;
import ru.chulkova.socialmediaapi.mapper.UserMapper;
import ru.chulkova.socialmediaapi.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserRepository repository;

    @GetMapping
    public List<ProfileDto> getAll() {
        log.info("get all users");
        return repository.findAll()
                .stream()
                .map(user -> UserMapper.getProfile(user))
                .collect(Collectors.toList());
    }
}