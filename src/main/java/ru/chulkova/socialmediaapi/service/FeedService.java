package ru.chulkova.socialmediaapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.chulkova.socialmediaapi.dto.PostDto;
import ru.chulkova.socialmediaapi.mapper.PostMapper;
import ru.chulkova.socialmediaapi.model.User;
import ru.chulkova.socialmediaapi.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PostRepository repository;

    public List<PostDto> getFeed(User user) {
        return repository.getPostsByFriends(user.id(),
                        PageRequest.of(0, 5,
                                Sort.by("date_time").descending()))
                .stream().map(PostMapper::getTo)
                .collect(Collectors.toList());
    }
}