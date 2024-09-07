package org.example.nasa_api.service;

import org.example.nasa_api.entity.NasaNeoWsDocument;
import org.example.nasa_api.model.NasaNeoWs;
import org.example.nasa_api.model.NearEarthObject;
import org.example.nasa_api.properties.NasaApiProperties;
import org.example.nasa_api.repository.NasaNeoWsRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class NasaNeoWsService {
    private final WebClient webClient;

    private final NasaApiProperties properties;

    private final NasaNeoWsRepository repository;


    public NasaNeoWsService(WebClient.Builder webClient, NasaApiProperties properties, NasaNeoWsRepository repository) {
        this.properties = properties;
        this.repository = repository;
        this.webClient = WebClient.builder()
                .baseUrl(properties.baseNeowsUrl())
                .defaultHeader("api_key", properties.apiKey())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    @Cacheable(value = "nasa-neows")
    public Mono<NasaNeoWs> getNeows(String startDate, String endDate) {
        var response = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/feed")
                                .queryParam("api_key", properties.apiKey())
                                .queryParamIfPresent("start_date", Optional.ofNullable(startDate))
                                .queryParamIfPresent("end_date", Optional.ofNullable(endDate))
                                .build())
                .retrieve()
                .bodyToMono(NasaNeoWs.class)
                .log();

        NasaNeoWs responseBlock = response.block();
        repository.insert(new NasaNeoWsDocument().setNeoWsData(responseBlock));

        return response;
    }

    @Cacheable("nasa-neo-asteroid")
    public Mono<NearEarthObject> getAsteroidById(int asteroidId) {
        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/neo/{asteroidId}")
                        .queryParam("api_key", properties.apiKey())
                        .build(asteroidId))
                .retrieve()
                .bodyToMono(NearEarthObject.class)
                .log();
        return response;
    }

}
