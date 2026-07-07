package ru.one.stream.internetsercher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.one.stream.internetsercher.service.ProxyService;

@RestController
@RequestMapping("/api/v1/proxy")
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyService proxyService;

    @GetMapping
    public ResponseEntity<byte[]> proxyAudio(
            @RequestParam String url,
            @RequestHeader(value = "Range", required = false) String range) {
        return proxyService.proxyAudio(url, range);
    }
}

