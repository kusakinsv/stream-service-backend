package ru.one.stream.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

;

@Entity
@Table(name = "pattern", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title")})

@Data
public class Pattern {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pattern_id_seq")
    @SequenceGenerator(name = "pattern_id_seq",  sequenceName = "pattern_id_seq", initialValue = 50)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "patterns", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @Cascade({org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<MusicTrack> tracks = new HashSet<>();

    public Pattern() {
    }

    public Pattern(String title) {
        this.title = title.toLowerCase();
    }

    public Pattern(Long id, String title, Set<MusicTrack> tracks) {
        this.id = id;
        this.title = title.toLowerCase();
        this.tracks = tracks;
    }

    public void setTitle(String title) {
        this.title = title.toLowerCase();
    }




}
