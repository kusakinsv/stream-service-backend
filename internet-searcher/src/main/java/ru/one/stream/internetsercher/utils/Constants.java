package ru.one.stream.internetsercher.utils;

import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static final Set<String> IGNORABLE_RESOURCES = new HashSet<>(){{
        add("youtube");
        add("google");
        add("mail.ru");
        add("rambler.ru");
        add("yahoo.com");
        add("yandex");
    }};
}
