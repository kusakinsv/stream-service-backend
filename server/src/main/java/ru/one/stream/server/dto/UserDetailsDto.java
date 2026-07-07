package ru.one.stream.server.dto;

import lombok.*;

import java.util.List;

@Data
public class UserDetailsDto {
    private Long id;
    private String username;
    private List<String> roles;

}


