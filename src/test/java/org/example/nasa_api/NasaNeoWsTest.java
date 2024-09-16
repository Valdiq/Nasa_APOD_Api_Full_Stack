package org.example.nasa_api;

import org.example.nasa_api.model.*;
import org.example.nasa_api.service.NasaNeoWsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.nullable;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NasaNeoWsTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private NasaNeoWsService service;

    private NasaNeoWs neoWs;

    private NearEarthObject asteroid;

    @BeforeEach
    void setUp() {

        asteroid = new NearEarthObject("4356", "Asteroid_4356", new EstimatedDiameter(new Kilometers(73445.7, 83567.9)), true, List.of(new CloseApproachData(new MissDistance("469665932"), "Mercury"), new CloseApproachData(new MissDistance("8766445"), "Mercury")));

        List<NearEarthObject> nearEarthObject = List.of(asteroid, new NearEarthObject("1", "Name1", new EstimatedDiameter(new Kilometers(1223.43, 2331.51)), false, List.of(new CloseApproachData(new MissDistance("3324654"), "Earth"), new CloseApproachData(new MissDistance("3324654"), "Earth"))),
                new NearEarthObject("2", "Name2", new EstimatedDiameter(new Kilometers(3332.5, 4678.51)), false, List.of(new CloseApproachData(new MissDistance("7862245"), "Earth"), new CloseApproachData(new MissDistance("7765644"), "Mars"))),
                new NearEarthObject("3", "Name3", new EstimatedDiameter(new Kilometers(5673.3, 9534.64)), true, List.of(new CloseApproachData(new MissDistance("7342586"), "Mars"), new CloseApproachData(new MissDistance("3242348"), "Jupiter"))));

        Map<String, List<NearEarthObject>> map = new HashMap<>();
        map.put("key_1", nearEarthObject);

        neoWs = new NasaNeoWs(map);

        Mockito.when(service.getNeows(nullable(String.class), nullable(String.class)))
                .thenReturn(Mono.just(neoWs));

        Mockito.when(service.getAsteroidById(nullable(Integer.class)))
                .thenReturn(Mono.just(asteroid));
    }

    @Test
    void shouldReturnNasaNeoWsWhenNoParametersProvided() {
        webTestClient.get()
                .uri("/neows/feed")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(NasaNeoWs.class)
                .value(resp -> {
                    assertThat(resp.nearEarthObjects()).hasSameSizeAs(neoWs.nearEarthObjects());
                    assertionForNasaNeoWs(resp, neoWs);
                });
    }

    @Test
    void shouldReturnAsteroidWhenValidIdIsProvided() {
        webTestClient.get()
                .uri("/neows/neo/4356")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(NearEarthObject.class)
                .value(resp -> {
                    assertThat(resp.id()).isEqualTo("4356");
                    assertionForNasaNeoswAsteroid(resp, asteroid);
                });
    }

    @Test
    void shouldNOTReturnAsteroidWhenInvalidIdIsProvided() {
        webTestClient.get()
                .uri("/neows/neo/999")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(NearEarthObject.class)
                .value(resp -> {
                    assertThat(resp.id()).isNotEqualTo("999");
                });
    }

    private void assertionForNasaNeoWs(NasaNeoWs actual, NasaNeoWs expected) {
        var actualNeoWsMap = actual.nearEarthObjects();
        var expectedNeoWsMap = expected.nearEarthObjects();

        for (String key : actualNeoWsMap.keySet()) {
            assertThat(actualNeoWsMap.get(key)).isEqualTo(expectedNeoWsMap.get(key));
        }
    }

    private void assertionForNasaNeoswAsteroid(NearEarthObject actual, NearEarthObject expected) {
        assertThat(actual.closeApproachData()).isEqualTo(expected.closeApproachData());
        assertThat(actual.estimatedDiameter()).isEqualTo(expected.estimatedDiameter());
        assertThat(actual.isHazardous()).isEqualTo(expected.isHazardous());
        assertThat(actual.name()).isEqualTo(expected.name());
    }

}
