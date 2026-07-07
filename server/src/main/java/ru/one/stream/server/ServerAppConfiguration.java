package ru.one.stream.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;
import ru.one.stream.server.service.SearchService;
import ru.one.stream.server.service.MusicSearchService;

@Configuration
@ComponentScan("ru.one.stream.server")
@PropertySource("classpath:application.yaml")
public class ServerAppConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }


    @Bean
    public SearchService searchService() {
        return new MusicSearchService();
    }
}
