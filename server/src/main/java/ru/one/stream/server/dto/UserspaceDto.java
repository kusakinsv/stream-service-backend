package ru.one.stream.server.dto;

import lombok.Data;

import java.util.LinkedList;

@Data
public class UserspaceDto {

    private String username;

    private LinkedList<PlaylistDto> playlists;

}
