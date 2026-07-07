package ru.one.stream.internetsercher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.one.stream.internetsercher.models.MusicTrackResult;
import ru.one.stream.internetsercher.models.SearchedMusicTrack;
import ru.one.stream.internetsercher.service.MainSearchService;
import ru.one.stream.internetsercher.service.UniversalSearchEngine;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MusicSearchController {
    private final MainSearchService searchService;
    private final UniversalSearchEngine uniService;

    @CrossOrigin
    @GetMapping("/search")
    public ResponseEntity<?> searchTrack(@RequestParam(value = "query") String trackName) {
        List<MusicTrackResult> tracks = searchService.search(trackName);
        System.out.println(tracks.size());
        tracks.forEach(System.out::println);
        return ResponseEntity.ok(tracks);
    }
}

