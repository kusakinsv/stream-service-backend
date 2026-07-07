package ru.one.stream.internetsercher.service.searchsystems;

import java.util.Set;

/**
 * Осуществляет поиск сайтов с музыкой
 */
public interface SearchSystemMusicFinder {

    Set<String> searchLinks(String query);
}
