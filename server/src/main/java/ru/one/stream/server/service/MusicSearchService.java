package ru.one.stream.server.service;

import ru.one.stream.server.dto.MusicTrackDto;

import java.util.List;

public class MusicSearchService implements SearchService {

    @Override
    public List<MusicTrackDto> searchMusicTrack() {
        return List.of();
    }
}
