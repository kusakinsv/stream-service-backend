package ru.one.stream.internetsercher.service.searchsystems;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import ru.one.stream.internetsercher.utils.Utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class BraveSearch implements SearchSystemMusicFinder {
    public static final String SEARCH_URL = "https://search.brave.com/search?q=";
    private final static String download = "+скачать&source=desktop";

    @SneakyThrows
    public Set<String> searchLinks(String name) {
        Set<String> linksList = new HashSet<>();
        String query = SEARCH_URL + Utils.toConvertedStringWithPlus(name) + download;
        Document document = Jsoup.connect(query)
                .followRedirects(true)
                .maxBodySize(0)
                .timeout(10000)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36")
                .referrer("http://www.google.com")
                .get();

//        System.out.println(document.html().toString());
        Elements links = document.body().select(new Evaluator.Attribute("href"));
        for (Element link : links) {
            System.out.println(link.attribute("href").getValue().toString());
        }

        System.out.println("Brave: " + linksList.size());
        return linksList;
    }

    private String toReadableLink(String link) {
        String readableLink = URLDecoder.decode(URLDecoder.decode(link, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        if (!readableLink.isEmpty()) readableLink = readableLink.split("q=")[1].split("&sa=")[0];
        return readableLink;
    }

}
