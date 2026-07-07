package ru.one.stream.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaylistDto {

    private Long id;

    private String title;

    private List<ItemDto> positions;

}
