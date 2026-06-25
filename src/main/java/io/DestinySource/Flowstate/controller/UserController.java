package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/site-hosts")
    public ResponseEntity<List<String>> getSiteHosts(@PathVariable Long userId) {
        List<String> hosts = userService.getUserSiteHosts(userId);
        return ResponseEntity.ok(hosts);
    }
}