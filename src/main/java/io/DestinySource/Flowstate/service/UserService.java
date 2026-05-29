package io.DestinySource.Flowstate.service;

import io.DestinySource.Flowstate.dto.RegisterRequestDTO;
import io.DestinySource.Flowstate.exception.BadRequestException;
import io.DestinySource.Flowstate.model.Role;
import io.DestinySource.Flowstate.model.User;
import io.DestinySource.Flowstate.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        user.setRole(Role.user);
        user.setEnabled(true);

        return userRepository.save(user);
    }
}