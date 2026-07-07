package ru.one.stream.server.mapper;

import ru.one.stream.server.dto.MusicTrackDto;
import ru.one.stream.server.utils.TrackUtils;

public class MusicTrackMapperOld {

    public MusicTrackDto toMusicTrackDto(String url) {
        MusicTrackDto musicTrackDto = new MusicTrackDto();
        musicTrackDto.setTitle(TrackUtils.constructNameFromLink(url));
        musicTrackDto.setUrl(url);
        return musicTrackDto;
    }

    public MusicTrackDto createMusicTrackDto(String name, String url) {
        MusicTrackDto musicTrackDto = new MusicTrackDto();
        musicTrackDto.setTitle(name);
        musicTrackDto.setUrl(url);
        return musicTrackDto;
    }
}
