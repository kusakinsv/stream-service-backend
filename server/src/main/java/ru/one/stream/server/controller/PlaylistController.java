package ru.one.stream.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.one.stream.server.dto.ItemDto;
import ru.one.stream.server.dto.MusicTrackDto;
import ru.one.stream.server.dto.PlaylistDto;
import ru.one.stream.server.service.PlaylistService;

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
        System.out.println(musicTrackDto);
        var library = playlistService.deletePosition(USERNAME, musicTrackDto);
        return ResponseEntity.ok(library);
    }


}
