package org.example.squaregameapi.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.example.squaregameapi.dto.StatsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class StatsClientService {

    private final WebClient webClient;

    public StatsClientService(@Value("${stats.api.url}") String statsApiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(statsApiUrl)
                .build();
    }

    @CircuitBreaker(name = "statsapi", fallbackMethod = "sendStatFallback")
    public void sendStat(Long gameId, Long userId, String oucome){
        log.info('Sending stat: gameId={}, userId={}, outcome={}', gameId, userId, outcome);

        StatsDTO statsDTO = new StatsDTO(

        )
    }

}
