//package ru.one.stream.desktop;
//
//
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.one.stream.server.dto.MusicTrackDto;
//import ru.one.stream.client.service.SearchEnginesService;
//import ru.one.stream.client.service.SearchService;
//
//import java.util.List;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class MainTest {
//    private final static String SONG_NAME = "Остановите пленку";
//
//    @Test
//    @Order(1)
//    public void downloadTest() {
//        DownloadService ds = new DownloadService();
//        SearchService searchService = new SearchEnginesService();
//        List<MusicTrackDto> results = searchService.findMusicTrack(SONG_NAME);
//        for (MusicTrackDto result : results) {
//            System.out.println(result);
//        }
//    }
//
//
//}