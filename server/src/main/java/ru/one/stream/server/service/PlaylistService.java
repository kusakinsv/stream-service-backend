package ru.one.stream.server.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.one.stream.server.dto.ItemDto;
import ru.one.stream.server.dto.MusicTrackDto;
import ru.one.stream.server.dto.PlaylistDto;
import ru.one.stream.server.entities.MusicTrack;
import ru.one.stream.server.entities.Playlist;
import ru.one.stream.server.entities.PlaylistPosition;
import ru.one.stream.server.mapper.PlaylistMapper;
import ru.one.stream.server.repositories.PlaylistPositionRepository;
import ru.one.stream.server.repositories.PlaylistRepository;
import ru.one.stream.server.repositories.UserRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class PlaylistService {
    private PlaylistRepository playlistRepository;
    private PlaylistPositionRepository playlistPositionRepository;
    private PlaylistMapper playlistMapper;
    private UserRepository userRepository;
    private MusicTrackService musicTrackService;

    public PlaylistDto getLibrary(String username) {
        return userRepository.findUserByUsername(username).get()
                .getPlaylists().stream().filter(Playlist::getIsMain)
                .findFirst()
                .map(playlistMapper::toDto)
                .orElseThrow();
    }

    public PlaylistDto addMusicTrackToLibrary(String username, MusicTrackDto musicTrackDto) {
        MusicTrack musicTrack = musicTrackService.addMusicTrack(musicTrackDto);

        Playlist playlist = userRepository.findUserByUsername(username).get()
                .getPlaylists().stream().filter(Playlist::getIsMain)
                .findFirst().orElseThrow();

        Set<PlaylistPosition> positions = playlist.getPlaylistPositions();
        //убедимся что трек не повторяется
        if (positions.stream().noneMatch(pos -> pos.getMusicTrack().getUrl().equals(musicTrack.getUrl()))) {
            int lastPosition = findLastPosition(positions);
            PlaylistPosition newPosition = new PlaylistPosition();
            newPosition.setPosition(lastPosition + 1);
            newPosition.setMusicTrack(musicTrack);
            newPosition.setTitle(musicTrackDto.getTitle());
            playlist.getPlaylistPositions().add(newPosition);
            return playlistMapper.toDto(playlistRepository.save(playlist));
        } else {
            return playlistMapper.toDto(playlist);
        }
    }

    private int findLastPosition(Collection<PlaylistPosition> positions) {
        return positions.stream().max(Comparator.comparing(PlaylistPosition::getPosition))
                .map(PlaylistPosition::getPosition)
                .orElse(0);
    }

    @Transactional
    public PlaylistDto deletePosition(String username, ItemDto musicTrackDto) {

        Playlist playlist = userRepository.findUserByUsername(username).get()
                .getPlaylists().stream().filter(Playlist::getIsMain)
                .findFirst().orElseThrow();

        Set<PlaylistPosition> positions = playlist.getPlaylistPositions();
        //убедимся что трек не повторяется
        Optional<PlaylistPosition> optional = positions.stream()
                .filter(x -> x.getMusicTrack().getUrl().equals(musicTrackDto.getUrl())).findFirst();
        if (optional.isPresent()) {
            PlaylistPosition position = optional.get();
            positions.remove(position);
            reOrderPositions(positions);
            return playlistMapper.toDto(playlistRepository.save(playlist));
        } else {
            return playlistMapper.toDto(playlist);
        }
    }

    @Transactional
    public PlaylistDto deletePosition(String username, Integer positionNumber) {

        Playlist playlist = userRepository.findUserByUsername(username).get()
                .getPlaylists().stream().filter(Playlist::getIsMain)
                .findFirst().orElseThrow();

        Set<PlaylistPosition> positions = playlist.getPlaylistPositions();
        //убедимся что трек не повторяется
        Optional<PlaylistPosition> optional = positions.stream().filter(x -> x.getPosition().equals(positionNumber)).findFirst();
        if (optional.isPresent()) {
            PlaylistPosition position = optional.get();
            positions.remove(position);
            reOrderPositions(positions);
            return playlistMapper.toDto(playlistRepository.save(playlist));
        } else {
            return playlistMapper.toDto(playlist);
        }
    }

    @Transactional
    public void reOrderPositions(Collection<PlaylistPosition> unOrdered) {
        List<PlaylistPosition> positions = unOrdered.stream()
                .sorted(Comparator.comparing(PlaylistPosition::getPosition))
                .toList();

        int offset = positions.size() + 1;

        for (int i = 0; i < positions.size(); i++) {
            PlaylistPosition pos = positions.get(i);
            pos.setPosition(i + 1 + offset); // временно ставим большие номера
        }
        playlistPositionRepository.saveAll(positions);
        playlistPositionRepository.flush();

        for (int i = 0; i < positions.size(); i++) {
            PlaylistPosition pos = positions.get(i);
            pos.setPosition(i + 1);
        }
        playlistPositionRepository.saveAll(positions);
    }

    public PlaylistDto getPlayList(Long playlistId) {
        return playlistRepository.findById(playlistId).stream()
                .findFirst()
                .map(playlistMapper::toDto)
                .orElseThrow();
    }

    public List<Long> getPlaylistsId(String username) {
        return userRepository.findUserByUsername(username).get()
                .getPlaylists()
                .stream()
                .map(Playlist::getId).toList();
    }
}
