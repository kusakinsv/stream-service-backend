package ru.one.stream.server.service;

import ru.one.stream.server.dto.MusicTrackDto;

import java.util.List;

public interface SearchService {

        List<MusicTrackDto> searchMusicTrack();
}
