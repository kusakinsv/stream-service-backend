package ru.one.stream.internetsercher.utils;

public class Utils {
    public static String toConvertedStringWithPlus(String name) {
        return name.toLowerCase().replaceAll(" ", "+");
    }

    public static String toConvertedStringWithSpace(String name) {
        return name.toLowerCase().replaceAll(" ", "%20");
    }

    public String constuctName(String link) {
        return link.replaceAll(".mp3", "")
                .replaceAll("https://", "")
                .replaceAll("http://", "");
    }
}
