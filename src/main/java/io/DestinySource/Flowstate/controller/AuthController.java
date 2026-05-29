package io.DestinySource.Flowstate.controller;

import io.DestinySource.Flowstate.dto.LoginRequestDTO;
import io.DestinySource.Flowstate.dto.LoginResponseDTO;
import io.DestinySource.Flowstate.dto.RegisterRequestDTO;
import io.DestinySource.Flowstate.model.User;
import io.DestinySource.Flowstate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User registeredUser = userService.registerNewUser(registerRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Gebruiker succesvol geregistreerd met ID: " + registeredUser.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = userService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
}