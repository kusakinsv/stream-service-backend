package ru.one.stream.desktop;

import ru.one.stream.server.utils.TrackUtils;

import java.nio.file.Paths;


public class DownloadService {
    private static final String path = Paths.get("").toAbsolutePath() + "/output/";
    private final Downloader downloader = new Downloader();

    public void downloadTrack(String url) {
        downloader.saveFileByUrl(url, TrackUtils.constructNameFromLink(url), path);
    }


}
