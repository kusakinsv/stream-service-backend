package ru.one.stream.server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.one.stream.server.dto.MusicTrackDto;
import ru.one.stream.server.entities.MusicTrack;
import ru.one.stream.server.entities.Pattern;
import ru.one.stream.server.entities.Playlist;
import ru.one.stream.server.entities.PlaylistPosition;
import ru.one.stream.server.repositories.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MusicTrackServiceOld {

    private final MusicTrackRepository musicTrackRepository;
    private final PatternRepository patternRepository;
//    private final MusicTrackFullTextRepository fullTextSearchRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final PlaylistPositionRepository playlistPositionRepository;

    //todo допустим, чтобы искал не по всем паттернам а только у этого трека
    public MusicTrack addMusicTrack(String url, String patternTitle) {
        Optional<MusicTrack> optionalMusicTrack = musicTrackRepository.findByUrl(url);
        Optional<Pattern> foundedPattern = patternRepository.findByTitleLike(patternTitle.toLowerCase());
        if (optionalMusicTrack.isPresent()) {
            MusicTrack foundedTrack = optionalMusicTrack.get();
            if (foundedPattern.isPresent()) {
                foundedTrack.getPatterns().add(foundedPattern.get());
            } else {
                foundedTrack.getPatterns().add(new Pattern(patternTitle));
            }
            return musicTrackRepository.save(foundedTrack);
        } else {
            MusicTrack newTrack = new MusicTrack();
            newTrack.setTitle(patternTitle);
            newTrack.setUrl(url);
            newTrack.setCreationDate(LocalDate.now());
            if (foundedPattern.isPresent()) {
                newTrack.getPatterns().add(foundedPattern.get());
            } else {
                newTrack.getPatterns().add(new Pattern(patternTitle));
            }
            return musicTrackRepository.save(newTrack);
        }
    }

    public MusicTrackDto addMusicTrackToPlayList(MusicTrackDto musicTrackDto, Long playListId) {
        MusicTrack musicTrack = this.addMusicTrack(musicTrackDto.getUrl(), musicTrackDto.getTitle());
        Playlist playlist = playlistRepository.getById(playListId);
        Set<PlaylistPosition> positions = playlist.getPlaylistPositions();
        int lastPosition = positions.size();
        PlaylistPosition newPlaylistPosition = new PlaylistPosition();
        newPlaylistPosition.setPosition(lastPosition + 1);
        newPlaylistPosition.setMusicTrack(musicTrack);
        newPlaylistPosition.setTitle(musicTrackDto.getTitle());
        positions.add(newPlaylistPosition);
        playlistRepository.save(playlist);
        musicTrackDto.setId(musicTrack.getId());
        return musicTrackDto;
    }


//    public MusicTrackDto addMusicTrackToLibrary(MusicTrackDto musicTrackDto, String username) {
//        MusicTrack musicTrack = this.addMusicTrack(musicTrackDto.getUrl(), musicTrackDto.getName());
//        Playlist playlist = userRepository.findUserByUsername(username).get().getPlaylist().stream().findFirst().get();
//        var positions = playlist.getPlaylistPositions();
//        int lastPosition = positions.size();
//        PlaylistPosition newPlaylistPosition = new PlaylistPosition();
//        newPlaylistPosition.setPosition(lastPosition + 1);
//        newPlaylistPosition.setMusicTrack(musicTrack);
//        newPlaylistPosition.setTitle(musicTrackDto.getName());
//        positions.add(newPlaylistPosition);
//        playlistRepository.save(playlist);
//        musicTrackDto.setId(musicTrack.getId());
//        return musicTrackDto;
//    }

    public void deleteMusicTrackFromLibrary(String username, int positionNumber) {
        List<PlaylistPosition> playlistPositions = playlistPositionRepository
                .findAllPlaylistPositionsByUsername(username);
        var positionForDelete = playlistPositions.stream().filter(pos -> pos.getPosition() == positionNumber).findFirst().get();
        playlistPositionRepository.delete(positionForDelete);
        playlistPositions.remove(positionForDelete);
        reOrderPositions(playlistPositions);
    }

    private List<PlaylistPosition> reOrderPositions(Collection<PlaylistPosition> unordered) {
        List<PlaylistPosition> positions = unordered.stream()
                .sorted(Comparator.comparing(PlaylistPosition::getPosition)).toList();
        for (int i = 0; i < positions.size(); i++) {
            positions.get(i).setPosition(i + 1);
        }
        return playlistPositionRepository.saveAll(positions);
    }

    public void renameMusicTrack(String username,
                                 Long playlistId,
                                 int positionNumber,
                                 @NotBlank @NotNull String newPositionTitle) {
        var position = playlistPositionRepository
                .findPlaylistPositionByUsernameAndPlaylistIdAndPostitionNumber(username, playlistId, positionNumber)
                .orElseThrow(() -> new RuntimeException("Can't find position with this parameters"));
        position.setTitle(newPositionTitle);
        var savedPosition = playlistPositionRepository.save(position);
        //todo сделать проверку на осмысленные слова, чтоб вперемешку букв не было, и знаков
        if (newPositionTitle.length() >= 10 && newPositionTitle.length() <= 50 ) addNewTitleAsPattern(savedPosition, newPositionTitle);
    }

    void addNewTitleAsPattern(PlaylistPosition playlistPosition, String newPositionsTitle) {
        var track = playlistPosition.getMusicTrack();
        var pattern = new Pattern(newPositionsTitle);
        track.getPatterns().add(pattern);
        musicTrackRepository.save(track);
    }


//    /**
//     * Полнотекстовый поиск
//     */
//    public List<MusicTrack> findMusicTrackByPatternTitle(String name) {
//        return fullTextSearchRepository.findMusicTrackByPatternTitle(name);
//    }
}
