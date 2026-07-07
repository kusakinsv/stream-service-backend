package ru.one.stream.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.one.stream.server.dto.MusicTrackDto;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/music")
public class MusicTrackController {

    @PostMapping
    public ResponseEntity<?> addTrack(@RequestBody MusicTrackDto dto) {

        String username = "admin";


        return ResponseEntity.ok(new Object());
    }




}

