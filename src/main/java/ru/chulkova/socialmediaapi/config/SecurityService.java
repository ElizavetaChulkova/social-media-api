package ru.chulkova.socialmediaapi.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.chulkova.socialmediaapi.exception.ResourceNotFoundException;

import java.security.Principal;

@Service
public class SecurityService {

    public UserDetails convertPrincipal(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            return (UserDetails) authenticationToken.getPrincipal();
        } else {
            throw new ResourceNotFoundException("Unknown principal type");
        }
    }
}
