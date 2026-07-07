package ru.one.stream.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.one.stream.server.dto.MusicTrackDto;
import ru.one.stream.server.entities.*;
import ru.one.stream.server.repositories.MusicTrackRepository;
import ru.one.stream.server.repositories.UserRepository;


@Component
@RequiredArgsConstructor
public class PlaylistMusicServiceOld {
    public final UserRepository userRepository;
    private final MusicTrackRepository musicTrackRepository;


//    public ItemDto addMusicTrackToUser(MusicTrackDto musicTrackDto, String username) {
//        User user = userRepository.findUserByUsername(username).get();
//        Playlist playlist = user.getPlaylist().stream().filter(Playlist::isMain).findFirst().get();
//
//        return null;
//    }

    public void addMusicTrackToPlayList(String username, String playListName, MusicTrackDto musicTrackDto) {
        User user = userRepository.findUserByUsername(username).get();
        //Получить плейлист с именем.

        //Найти треки по урл

        //Создать новую позицию и добавить трек.

        //Всять название трека и прикрепить новый паттерн к треку если такой трек уже есть

        //Если трека нет, то создать новый и посмотреть есть ли такие паттерны. Если есть то прикрепить паттерн к треку.
        //Если паттернов нет. То создаем новый паттерн и прикрепляем к треку.
    }

    public MusicTrackDto addMusicTrack(MusicTrackDto musicTrackDto) {
        var trackFromRepo = musicTrackRepository.findByUrl(musicTrackDto.getUrl());
        if (trackFromRepo.isEmpty()) {
            MusicTrack musicTrack = new MusicTrack();
            musicTrack.setTitle(musicTrackDto.getTitle());
            musicTrack.setUrl(musicTrackDto.getUrl());
            Pattern pattern = new Pattern();
            pattern.setTitle(musicTrackDto.getTitle());
            pattern.getTracks().add(musicTrack);
            musicTrack.getPatterns().add(pattern);
            MusicTrack result = musicTrackRepository.save(musicTrack);
            musicTrackDto.setId(musicTrack.getId());
            return musicTrackDto;
        } else {
            musicTrackDto.setId(trackFromRepo.get().getId());
            return musicTrackDto;
        }
    }

}
