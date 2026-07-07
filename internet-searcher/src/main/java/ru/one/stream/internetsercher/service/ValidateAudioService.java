package ru.one.stream.internetsercher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import ru.one.stream.internetsercher.models.SearchedMusicTrack;
import ru.one.stream.internetsercher.models.ValidationResult;

import java.io.IOException;
import java.util.Set;
@Slf4j
@Service
@RequiredArgsConstructor
public class ValidateAudioService {
    private final CloseableHttpClient httpClient;

    private static final Set<String> SUPPORTED_AUDIO_TYPES = Set.of(
            "audio/mpeg",
            "audio/mp3",
            "audio/x-mp3",
            "audio/mp4",
            "audio/x-m4a",
            "audio/ogg",
            "audio/wav",
            "audio/x-wav",
            "audio/flac",
            "audio/aac"
    );

    public ValidationResult validateTrack(SearchedMusicTrack track) {
        try {
            log.debug("Валидируем {}", track.getUrl());
            String finalUrl = getFinalUrl(track.getUrl());
            log.debug("finalUrl: {}", finalUrl);
            HttpGet get = new HttpGet(finalUrl);
            get.setHeader("User-Agent", "Mozilla/5.0");
            get.setHeader("Range", "bytes=0-1023");
            get.setHeader("Accept", "audio/*");
            try (CloseableHttpResponse response = httpClient.execute(get)) {

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode >= 404) {
                    return ValidationResult.invalid(track.getUrl());
                }

                // Получаем заголовки
                String contentType = getHeader(response, "Content-Type");
                long contentLength = getContentLength(response);

                if (!isAudio(contentType, contentLength)) {
                    return ValidationResult.invalid(track.getUrl());
                }

//                boolean supportsRanges = "bytes".equals(getHeader(response, "Accept-Ranges"));
//                if (!supportsRanges) {
//                    return ValidationResult.invalid(url);
//                }


                // Получаем первые байты для проверки магических байтов
//                byte[] firstBytes = EntityUtils.toByteArray(response.getEntity());
//                boolean isMp3 = validateMagicBytes(firstBytes);
//                if (!isMp3) {
//                    return ValidationResult.invalid(url);
//                }

                boolean corsSupported = checkCorsFromHeaders(response);
                boolean needProxy = !corsSupported;

                EntityUtils.consume(response.getEntity());
                return new ValidationResult(track.getTitle(), finalUrl, true, corsSupported);
            }

        } catch (Exception e) {
            return ValidationResult.invalid(track.getUrl());
        }

    }

    private String getFinalUrl(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0");
        try (CloseableHttpResponse response = httpClient.execute(get)) {
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 300 && statusCode < 400) {
                String location = getHeader(response, "Location");
                if (location != null) {
                    EntityUtils.consume(response.getEntity());
                    return getFinalUrl(location);
                }
            }

            // Если не редирект или нет Location - это конечный URL
            String finalUrl = response.getHeaders("Location").length > 0
                    ? response.getHeaders("Location")[0].getValue()
                    : url;

            EntityUtils.consume(response.getEntity());
            return finalUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isAudio(String contentType, long contentLength) {
        if (contentType == null) return false;
        boolean validContentType = SUPPORTED_AUDIO_TYPES.contains(contentType.toLowerCase());
        boolean validContentLength = contentLength > 1024;
        return validContentType && validContentLength;
    }

    private String getHeader(CloseableHttpResponse response, String name) {
        return response.getHeaders(name).length > 0
                ? response.getHeaders(name)[0].getValue()
                : null;
    }

    private long getContentLength(CloseableHttpResponse response) {
        // Сначала пытаемся получить из Content-Range
        String contentRange = getHeader(response, "Content-Range");
        if (contentRange != null) {
            // Пример: "bytes 0-1023/5242880"
            String[] parts = contentRange.split("/");
            if (parts.length == 2) {
                try {
                    return Long.parseLong(parts[1]);
                } catch (NumberFormatException e) {
                    // Игнор
                }
            }
        }

        String contentLength = getHeader(response, "Content-Length");
        if (contentLength != null) {
            try {
                return Long.parseLong(contentLength);
            } catch (NumberFormatException e) {
                // Игнор
            }
        }

        return -1;
    }

    private boolean checkCorsFromHeaders(CloseableHttpResponse response) {
        String allowOrigin = getHeader(response, "Access-Control-Allow-Origin");
        return allowOrigin != null &&
                (allowOrigin.equals("*") ||
                        allowOrigin.contains("localhost") ||
                        allowOrigin.equals("null"));
    }

    private boolean validateMagicBytes(byte[] bytes) {
        if (bytes == null || bytes.length < 4) return false;
        // MP3: FF FB, FF F3, FF F2, FF F7, FF FA
        if (bytes[0] == (byte) 0xFF && (bytes[1] & 0xE0) == 0xE0) {
            return true;
        } else return false;
    }


}
