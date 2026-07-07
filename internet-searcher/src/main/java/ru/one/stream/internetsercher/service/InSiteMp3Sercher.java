package ru.one.stream.internetsercher.service;


import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InSiteMp3Sercher {

    @SneakyThrows
    public Set<String> searchByLinks(Iterable<String> linksList) {
        ConcurrentLinkedQueue<String> urls = new ConcurrentLinkedQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(30);
        int counter = 0;
        AtomicInteger faultCounter = new AtomicInteger(0);
        for (String link : linksList) {
            counter++;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = unPackLink(link);
                        if (result != null) {
                            urls.add(result);
                        }
                    } catch (Exception ignore) {
                        faultCounter.getAndIncrement();
                    }

                }
            });

        }
        executor.shutdown();
        while(!executor.awaitTermination(5, TimeUnit.SECONDS)){}

//        System.out.println("Всего ссылок: " + counter + " Поиск не состоялся: " + faultCounter.get());
        return new HashSet<>(urls);
    }

    @SneakyThrows
    private String unPackLink(String link) {
        try {
            String result = null;
            Document document = Jsoup.connect(link)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.duckduckgo.com")
                    .timeout(6000)
                    .get();
            Elements elements = document.body().getElementsByAttributeValueEnding("href", ".mp3");
            for (Element element : elements) {
                String foundedLink = element.attr("href");
                if (isLink(foundedLink)) {
                    result = foundedLink;
                    break;
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка просмотра сайта: " + link, e);
        }
    }

    private boolean isLink(String link) {
        return ((link.startsWith("http:") || link.startsWith("https:"))
                && link.endsWith(".mp3"));
    }


}
