package ru.one.stream.internetsercher.service.searchsystems;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.one.stream.internetsercher.utils.Constants;
import ru.one.stream.internetsercher.utils.Utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Component
public class DuckDuckGoSearch implements SearchSystemMusicFinder {
    private final static String DDG_URL = "https://duckduckgo.com/?q=";
    private final static String download = "+скачать";
    private final static String otherSettings = "&hps=2";
    private final static int buttonClicks = 5;

    @SneakyThrows
    public Set<String> searchLinks(String name) {
        Set<String> linksList = new HashSet<>();
        String query = DDG_URL + Utils.toConvertedStringWithPlus(name) + "+" + download + otherSettings;
        Document document = Jsoup.connect(query)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.duckduckgo.com")
                .get();

        Elements elementList = document.body().select("h2");
        for (Element element : elementList) {
            String link = toReadableLink(element.child(0).attr("href"));
            if (Constants.IGNORABLE_RESOURCES.stream().noneMatch(link::contains)) {
                linksList.add(link);
            }
        }
//        try (WebClient webClient = new WebClient()) {
//            webClient.getOptions().setJavaScriptEnabled(true);
//            webClient.getOptions().setThrowExceptionOnScriptError(false);
//
//            // Загрузка страницы
//            HtmlPage page = webClient.getPage(query);
//            webClient.waitForBackgroundJavaScript(1000);
//            // ИЛИ по классу/ID
////            HtmlButton buttonByClass = page.querySelector("button#more-results");
//            int counter = 0;
////            while (counter < buttonClicks) {
//                HtmlButton buttonByClass = page.getFirstByXPath("//button[contains(text(), 'Больше результатов')]");
//            System.out.println(page.asXml());
//            if (buttonByClass != null) {
//                    // Кликаем на кнопку
//                    buttonByClass.click();
//                    // Ждем загрузки нового контента
//
//                    // Работаем с обновленной страницей
//                    System.out.println("Новый контент загружен!");
//                    counter++;
//                }else {
//                    System.out.println("Кнопка не найдена");
//                }
//                webClient.waitForBackgroundJavaScript(1000);
////            }
//            System.out.println(page.asXml());
//            List<HtmlElement> elems = page.getBody().getByXPath("//h2/a[@href]");
//            for (HtmlElement elem : elems) {
//                String link = elem.getAttribute("href");
//                System.out.println(link);
//            }
//        }
        return linksList;
    }

    private String toReadableLink(String link) {
        String readableLink = URLDecoder.decode(URLDecoder.decode(link, StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        readableLink = readableLink.split("uddg=")[1].split("&rut=")[0];
        return readableLink;
    }

}
/*    Elements elementList = document.body().getElementsByAttribute("href");
        for (Element element : elementList) {
            String link = toReadableLink(element.attr("href"));
            linksList.add(link);
        }*/