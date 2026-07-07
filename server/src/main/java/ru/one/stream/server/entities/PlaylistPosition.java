package ru.one.stream.server.entities;

import lombok.Data;

import jakarta.persistence.*;;

@Data
@Entity
@Table(name = "playlist_position", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"playlist_id", "position"})
})
public class PlaylistPosition {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="positions_id_seq")
    @SequenceGenerator(name = "positions_id_seq",  sequenceName = "positions_id_seq", initialValue = 50)
    private Long id;

    private Integer position;

    private String title;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "music_track_id")
    private MusicTrack musicTrack;

    public PlaylistPosition() {
    }
}
