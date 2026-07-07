package ru.one.stream.server.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.one.stream.server.dto.ItemDto;
import ru.one.stream.server.dto.PlaylistDto;
import ru.one.stream.server.entities.Playlist;
import ru.one.stream.server.entities.PlaylistPosition;

import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
        @Mapping(target = "positions", source = "playlistPositions")
        PlaylistDto toDto(Playlist playlist);

        @AfterMapping
        default void sortPositions(@MappingTarget List<ItemDto> positions) {
                positions.sort(Comparator.comparing(ItemDto::getPosition));
        }

        @Mapping(target = "duration", source = "playlistPosition.musicTrack.duration")
        @Mapping(target = "url", source = "playlistPosition.musicTrack.url")
        @Mapping(target = "isNeedProxy", source = "playlistPosition.musicTrack.isNeedProxy")
        ItemDto toDto(PlaylistPosition playlistPosition);
}
