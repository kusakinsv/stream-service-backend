package ru.one.stream.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.one.stream.server.entities.Pattern;

import java.util.Optional;

public interface PatternRepository extends JpaRepository<Pattern, Long> {

    Optional<Pattern> findByTitleLike(String title);
}
