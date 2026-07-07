package ru.one.stream.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.one.stream.server.dto.MusicTrackDto;
import ru.one.stream.server.entities.MusicTrack;
import ru.one.stream.server.entities.Pattern;
import ru.one.stream.server.repositories.MusicTrackRepository;
import ru.one.stream.server.repositories.PatternRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicTrackService {

    private final MusicTrackRepository musicTrackRepository;
    private final PatternRepository patternRepository;

    /**
     * Добавляет трек в общую Базу треков
     */
    public MusicTrack addMusicTrack(MusicTrackDto musicTrackDto) {
        String patternTitle = musicTrackDto.getTitle();
        Optional<MusicTrack> optionalMusicTrack = musicTrackRepository.findByUrl(musicTrackDto.getUrl());
        Optional<Pattern> foundedPattern = patternRepository.findByTitleLike(musicTrackDto.getTitle().toLowerCase());
        if (optionalMusicTrack.isPresent()) {
            MusicTrack foundedTrack = optionalMusicTrack.get();
            if (foundedTrack.getDuration() == null && musicTrackDto.getDuration() != null) {
                foundedTrack.setDuration(musicTrackDto.getDuration());
            }
            if (foundedPattern.isPresent()) {
                foundedTrack.getPatterns().add(foundedPattern.get());
            } else {
                foundedTrack.getPatterns().add(new Pattern(musicTrackDto.getTitle()));
            }
            return musicTrackRepository.save(foundedTrack);
        } else {
            MusicTrack newTrack = new MusicTrack();
            newTrack.setTitle(musicTrackDto.getTitle());
            newTrack.setUrl(musicTrackDto.getUrl());
            newTrack.setIsNeedProxy(musicTrackDto.getIsNeedProxy());
            newTrack.setDuration(musicTrackDto.getDuration());
            newTrack.setCreationDate(LocalDate.now());
            if (foundedPattern.isPresent()) {
                newTrack.getPatterns().add(foundedPattern.get());
            } else {
                newTrack.getPatterns().add(new Pattern(patternTitle));
            }
            return musicTrackRepository.save(newTrack);
        }
    }
}
