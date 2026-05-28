package io.DestinySource.Flowstate.dto;

public record RegisterRequestDTO(
    String username,
    String email,
    String password
){}