package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.LoginRequestDTO;
import io.DestinySource.Flowstate.dto.LoginResponseDTO;
import io.DestinySource.Flowstate.dto.RegisterRequestDTO;
import io.DestinySource.Flowstate.exception.BadRequestException;
import io.DestinySource.Flowstate.model.Role;
import io.DestinySource.Flowstate.model.User;
import io.DestinySource.Flowstate.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public User registerNewUser(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Gebruikersnaam is al in gebruik.");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("E-mailadres is al in gebruik.");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());

        String hashedPassword = passwordEncoder.encode(request.password());
        user.setPassword(hashedPassword);

        user.setRole(Role.ADMIN);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateAccessToken(user);

        return new LoginResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().name(), token);
    }
}