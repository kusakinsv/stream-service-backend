package ru.one.stream.internetsercher.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicTrackResult {
    private String title;
    private String url;
    private Boolean cors;
}
