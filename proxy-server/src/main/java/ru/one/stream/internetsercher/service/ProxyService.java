package ru.one.stream.internetsercher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProxyService {

    public static final MultiValueMap<String, String> CORS_HEADERS = new LinkedMultiValueMap<>() {{
        add("Access-Control-Allow-Origin", "*");
        add("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS");
        add("Access-Control-Expose-Headers", "Content-Range, Accept-Ranges");
    }};

    public static final String ZERO_RANGE = "bytes=0-1023";
    public static final String CONTENT_RANGE_HEADER = "Content-Range";

    private final RestTemplate restTemplate;

    public ResponseEntity<byte[]> proxyAudio(String url, String range) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");

            //Если браузер прислал Range - передаем его дальше
            if (range != null) {
                headers.set("Range", range);
                log.debug("Запрос части файла: " + range);
            } else {
                headers.set("Range", ZERO_RANGE);
                log.debug("Запрос части файла: " + ZERO_RANGE );
            }

            HttpEntity<?> entity = new HttpEntity<>(headers);

            //Запрашиваем файл с внешнего сервера
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    byte[].class
            );

            //Формируем ответ для браузера
            HttpHeaders responseHeaders = createResponseHeaders(response.getHeaders());

            //Проверяем, вернул ли сервер Partial Content (206)
            boolean isPartial = response.getStatusCode() == HttpStatus.PARTIAL_CONTENT;
            if (isPartial) {
                return ResponseEntity
                        .status(HttpStatus.PARTIAL_CONTENT)
                        .headers(responseHeaders)
                        .body(response.getBody());
            } else {
                return ResponseEntity
                        .ok()
                        .headers(responseHeaders)
                        .body(response.getBody());
            }

        } catch (Exception e) {
            log.error("Ошибка прокси", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public HttpHeaders createResponseHeaders(HttpHeaders headersFromResponse) {
        HttpHeaders responseHeaders = new HttpHeaders();
        // Копируем Content-Type
        if (headersFromResponse.getContentType() != null) {
            responseHeaders.setContentType(headersFromResponse.getContentType());
        }

        // Копируем Content-Length
        if (headersFromResponse.getContentLength() > 0) {
            responseHeaders.setContentLength(headersFromResponse.getContentLength());
        }

        // Копируем Content-Range (если есть)
        if (headersFromResponse.get(CONTENT_RANGE_HEADER) != null && !headersFromResponse.get(CONTENT_RANGE_HEADER).isEmpty()) {
            responseHeaders.set(CONTENT_RANGE_HEADER,
                    headersFromResponse.get(CONTENT_RANGE_HEADER).get(0));
        }

        responseHeaders.addAll(CORS_HEADERS);
        return responseHeaders;
    }

}
