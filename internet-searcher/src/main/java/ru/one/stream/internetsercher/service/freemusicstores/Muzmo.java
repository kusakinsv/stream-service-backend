package ru.one.stream.internetsercher.service.freemusicstores;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.one.stream.internetsercher.models.SearchedMusicTrack;
import ru.one.stream.internetsercher.utils.Utils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.util.HashSet;
import java.util.Set;

/**
 * su.muzmo.cc/
 * rux.muzmo.cc/
 */
@Component
public class Muzmo implements MusicResource {
//    public static final String URL = "https://su.muzmo.cc";
    public static final String URL = "https://rmr.muzmo.cc";
    public static final String IP = "http://5.79.82.86";//не работает
    public static final String QUERY_URL = URL + "/search?q=";
    public static final String QUERY_IP = IP + "/search?q=";

    @SneakyThrows
    @Override
    public Set<SearchedMusicTrack> search(String trackName) {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        String query = QUERY_URL + Utils.toConvertedStringWithPlus(trackName);
        Document document = Jsoup.connect(query)
                .followRedirects(true)
                .maxBodySize(0)
                .timeout(10000)
                .sslContext(sslContext)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/142.0.0.0 Safari/537.36")
                .referrer("http://www.google.com")
                .get();

        Set<SearchedMusicTrack> tracks = new HashSet<>();
        Elements elements = document.body().getElementsByAttribute("data-file");
        for (Element elem : elements.asList()) {
            String partUrl = elem.attr("data-file");
            String url = URL + partUrl;
            String finalName = elem.attr("data-title");
            SearchedMusicTrack musicTrackDto = new SearchedMusicTrack(finalName, url);
            tracks.add(musicTrackDto);
        }

        return tracks;
    }

    TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };
}
