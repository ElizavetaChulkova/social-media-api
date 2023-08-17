package ru.chulkova.socialmediaapi.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chulkova.socialmediaapi.config.SecurityService;
import ru.chulkova.socialmediaapi.model.User;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/demo-controller")
public class DemoController {

    private final SecurityService securityService;

//    @GetMapping
//    public ResponseEntity<String> sayHello(Principal principal) {
//        log.info("Invoke demo controller");
//        UserDetails jwtPrincipal = securityService.convertPrincipal(principal);
//        return ResponseEntity.ok("Hello from secured endpoint, " + jwtPrincipal.getUsername());
//    }

    @GetMapping
    public ResponseEntity<String> sayHello(@AuthenticationPrincipal User user) {
        log.info("Invoke demo controller");
        return ResponseEntity.ok("Hello from secured endpoint, " + user.getUsername());
    }
}
