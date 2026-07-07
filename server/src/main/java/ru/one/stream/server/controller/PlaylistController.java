package ru.one.stream.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.one.stream.server.dto.ItemDto;
import ru.one.stream.server.dto.MusicTrackDto;
import ru.one.stream.server.dto.PlaylistDto;
import ru.one.stream.server.service.PlaylistService;

import java.util.List;

@RestController
@RequestMapping("api/v1/library")
@RequiredArgsConstructor
public class PlaylistController {

    private static final String USERNAME = "admin";

    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<PlaylistDto> getLibrary() {
        var library = playlistService.getLibrary(USERNAME);
        return ResponseEntity.ok(library);
    }

    @PostMapping
    public ResponseEntity<PlaylistDto> addTrackToLibrary(@RequestBody MusicTrackDto musicTrackDto) {
        var library = playlistService.addMusicTrackToLibrary(USERNAME, musicTrackDto);
        return ResponseEntity.ok(library);
    }

    @DeleteMapping
    public ResponseEntity<PlaylistDto> deletePosition(@RequestBody ItemDto musicTrackDto) {
        var library = playlistService.deletePosition(USERNAME, musicTrackDto);
        return ResponseEntity.ok(library);
    }

    //todo Проверку пользователя добавить
    @PutMapping("playlist/{playlistId}")
    public ResponseEntity<PlaylistDto> reOrderPositions(@PathVariable Long playlistId,
                                                        @RequestBody List<ItemDto> positions) {
        playlistService.reOrderPositionsInPlaylistFromDto(playlistId, positions);
       var playlist =  playlistService.getPlayList(playlistId);
        return ResponseEntity.ok(playlist);
    }


}
