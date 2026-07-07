package ru.one.stream.server.service;

import ru.one.stream.server.dto.UserDetailsDto;


public interface UserService {

    UserDetailsDto getUserByUsername(String userName);
    UserDetailsDto getUserById(String userId);
    UserDetailsDto createNewUser(String username);

}