package org.example.nasa_api.service;

import org.example.nasa_api.entity.NasaAPODDocument;
import org.example.nasa_api.model.NasaAPOD;
import org.example.nasa_api.properties.NasaApiProperties;
import org.example.nasa_api.repository.NasaAPODRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class NasaAPODService {

    private final WebClient webClient;

    private final NasaApiProperties properties;

    private final NasaAPODRepository repository;


    public NasaAPODService(WebClient.Builder webClient, NasaApiProperties properties, NasaAPODRepository repository) {
        this.properties = properties;
        this.repository = repository;
        this.webClient = webClient.baseUrl(properties.baseApodUrl())
                .defaultHeader("api_key", properties.apiKey())
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    @Cacheable(value = "nasa-apod")
    public Mono<List<NasaAPOD>> getApod(String date, String startDate, String endDate, Integer count, Boolean thumbs) {

        var response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", properties.apiKey())
                        .queryParamIfPresent("date", Optional.ofNullable(date))
                        .queryParamIfPresent("start_date", Optional.ofNullable(startDate))
                        .queryParamIfPresent("end_date", Optional.ofNullable(endDate))
                        .queryParamIfPresent("count", Optional.ofNullable(count))
                        .queryParamIfPresent("thumbs", Optional.ofNullable(thumbs))
                        .build())
                .retrieve()
                .bodyToFlux(NasaAPOD.class)
                .collectList();

        List<NasaAPOD> responseList = response.block();
        for (NasaAPOD apod : responseList) {
            repository.insert(new NasaAPODDocument().setDate(apod.date()).setMediaUrl(apod.mediaUrl()));
        }

        return response;
    }
}
