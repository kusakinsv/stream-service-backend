package ru.one.stream.internetsercher.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.one.stream.internetsercher.models.MusicTrackResult;
import ru.one.stream.internetsercher.models.ValidationResult;
import ru.one.stream.internetsercher.service.freemusicstores.Muzmo;
import ru.one.stream.internetsercher.utils.VirtualExecutorService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainSearchService {
    public static final String proxyServerPart = "/api/v1/proxy?url=";

    private final VirtualExecutorService virtualExecutorService;
    private final ValidateAudioService validateAudioService;
    private final List<SearchEngine> musicResources = new ArrayList<>() {{
        add(new Muzmo());
//        add(new Mp3PartyNet());
    }};

    @Value("${stream-service.proxy-server.url}")
    private String proxyServerUrl;

    @SneakyThrows
    public List<MusicTrackResult> search(String trackName) {
        Set<CompletableFuture<MusicTrackResult>> featureList = ConcurrentHashMap.newKeySet();
        List<CompletableFuture<Void>> searchFutures = new ArrayList<>();

        //todo удалить передачу name в ValidationResult
        for (SearchEngine system : musicResources) {
            CompletableFuture<Void> searchFuture = CompletableFuture.supplyAsync(() -> system.search(trackName), virtualExecutorService)
                    .thenAcceptAsync(trackList -> {
                        if (trackList == null || trackList.isEmpty()) {
                            return;
                        }
                        trackList.stream()
                                .map(track -> CompletableFuture.supplyAsync(() -> {
                                            try {
                                                ValidationResult validationResult = validateAudioService.validateTrack(track);
                                                if (validationResult.isValid()) {
                                                    MusicTrackResult result = new MusicTrackResult();
                                                    result.setTitle(track.getTitle());
                                                    result.setUrl(validationResult.getUrl());
                                                    result.setCors(validationResult.isCorsSupported());
                                                    return result;
                                                } else {
                                                    return null;
                                                }
                                            } catch (Exception e) {
                                                log.warn("Ошибка валидации трека: {}", e.getMessage());
                                                return null;
                                            }
                                        }, virtualExecutorService)
                                        .exceptionally(ex -> {
                                            log.warn("Ошибка при валидации: {}", ex.getMessage(), ex);
                                            return null;
                                        })).forEach(featureList::add);
                    }, virtualExecutorService).exceptionally(ex -> {
                        log.warn("Ошибка поиска в системе: " + ex.getMessage());
                        return null;
                    });
            searchFutures.add(searchFuture);
        }

        try {
            //Завершение поиска
            CompletableFuture.allOf(searchFutures.toArray(new CompletableFuture[0]))
                    .get(20, TimeUnit.SECONDS);

            //Таймаут на валидацию
            CompletableFuture.allOf(featureList.toArray(new CompletableFuture[0]))
                    .get(15, TimeUnit.SECONDS);

            List<MusicTrackResult> allTracks = featureList.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .toList();

            System.out.println("Найдено треков: " + allTracks.size());
            return allTracks;
        } catch (TimeoutException e) {
            log.warn("Превышено время ожидания");
            List<MusicTrackResult> partialResults = featureList.stream()
                    .filter(CompletableFuture::isDone)
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .toList();
            log.debug("Найдено треков по таймауту: {}", partialResults.size());
            return partialResults;
        }
    }

    private String createProxyUrl(String url) {
        return proxyServerUrl + proxyServerPart + url;
    }

}
