package ru.one.stream.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.one.stream.server.entities.Playlist;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findAllByIdInAndIsMain(Collection<Long> id, boolean isMain);
}
