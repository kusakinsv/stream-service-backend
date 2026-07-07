package ru.one.stream.server.utils;

public class TrackUtils {

    public static String constructNameFromLink(String url) {
        if (url.contains("/")) {
            String[] array = url.split("/");
            url = array[array.length - 1];
        }
        return url.split(".mp3")[0];
    }
}
