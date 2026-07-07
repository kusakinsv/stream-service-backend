package ru.one.stream.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MusicTrackDto {

    private Long id;
    private String title;
    private Double duration;
    private String url;
    private Boolean isNeedProxy;

    @Override
    public String toString() {
        return String.format(
                "MusicTrackDto (id=%s, name=%s, duration=%s, url=%s, isNeedProxy=%s)", this.id, this.title, this.duration, this.url, this.isNeedProxy);
    }
}
