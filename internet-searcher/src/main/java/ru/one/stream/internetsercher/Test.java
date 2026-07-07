package ru.one.stream.internetsercher;

import ru.one.stream.internetsercher.service.InSiteMp3Sercher;
import ru.one.stream.internetsercher.service.SearchEngine;
import ru.one.stream.internetsercher.service.freemusicstores.MusicResource;
import ru.one.stream.internetsercher.service.freemusicstores.Muzmo;
import ru.one.stream.internetsercher.service.searchsystems.DuckDuckGoSearch;

import java.io.IOException;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws IOException {
        DuckDuckGoSearch duckGoSearch = new DuckDuckGoSearch();
        Set<String> links = duckGoSearch.searchLinks("Цой группа крови");
        InSiteMp3Sercher sercher = new InSiteMp3Sercher();
        sercher.searchByLinks(links).forEach(x-> System.out.println(x));
    }
}
