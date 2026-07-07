package ru.one.stream.server.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

;

@Data
@Entity
public class MusicTrack {
    @Transient
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="music_track_id_seq")
    @SequenceGenerator(name = "music_track_id_seq",  sequenceName = "music_track_id_seq", initialValue = 50)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "url", unique = true)
    private String url;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "music_track__pattern",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "pattern_id")
    )
    private Set<Pattern> patterns = new HashSet<>();

    @Column(name = "is_need_proxy", unique = true)
    private Boolean isNeedProxy;

    private LocalDate creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicTrack that = (MusicTrack) o;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "MusicTrack (id=%s, trackName=%s, url=%s)", this.id, this.title, this.url);
    }

    public MusicTrack() {
    }
}
