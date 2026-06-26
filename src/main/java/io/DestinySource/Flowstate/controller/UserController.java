package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.service.JwtService;
import io.DestinySource.Flowstate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/sites")
    public ResponseEntity<List<String>> getMySites(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        Long userId = jwtService.getUserIdFromToken(token, "access");

        List<String> hosts = userService.getUserSiteHosts(userId);
        return ResponseEntity.ok(hosts);
    }
}