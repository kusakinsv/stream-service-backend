package ru.one.stream.internetsercher.service;

import ru.one.stream.internetsercher.models.SearchedMusicTrack;

import java.util.Collection;

public interface SearchEngine {

    Collection<SearchedMusicTrack> search(String query);
}
