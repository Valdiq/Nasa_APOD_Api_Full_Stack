package org.example.nasa_api;

import org.example.nasa_api.model.NasaAPOD;
import org.example.nasa_api.service.NasaAPODService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.nullable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NasaAPODTest {

    @Autowired
    private WebTestClient webTestClient;

    private List<NasaAPOD> apodList;

    @MockBean
    private NasaAPODService service;


    @BeforeEach
    public void setUp() {
        apodList = List.of(new NasaAPOD("Copywriter_1", new Date(2003 - 1900, Calendar.DECEMBER, 21), "Description", "someMediaUrl.png", "Picture", "v1", "Some Title"),
                new NasaAPOD("Copywriter_2", new Date(2007 - 1900, Calendar.MAY, 7), "Description_2", "someMediaUrl_2.png", "Picture", "v1", "Some Title_2"),
                new NasaAPOD("Copywriter_3", new Date(2002 - 1900, Calendar.MARCH, 16), "Description_3", "someMediaUrl_3.mov", "Video", "v2", "Some Title_3"));

        Mockito.when(service.getApod(nullable(String.class), nullable(String.class),
                        nullable(String.class), nullable(Integer.class), nullable(Boolean.class)))
                .thenReturn(Mono.just(apodList));
    }

    @Test
    void shouldReturnMonoOfListOfAPODWithoutParametersAreProvided() {
        webTestClient.get()
                .uri("/apod")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(NasaAPOD.class)
                .value(response -> {
                    assertThat(response).hasSameSizeAs(apodList);
                    for (int i = 0; i < response.size(); i++) {
                        assertionForNasaAPODResponse(response.get(i), apodList.get(i));
                    }
                });
    }


    @Test
    void shouldReturnMonoOfListOfAPODWithValidStartDateParameterIsProvided() {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/apod")
                                .queryParam("start_date", "2001-11-23")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(NasaAPOD.class)
                .value(response -> {
                    assertThat(response).hasSameSizeAs(apodList);
                    for (int i = 0; i < response.size(); i++) {
                        assertionForNasaAPODResponse(response.get(i), apodList.get(i), "2001-11-23");
                    }
                });
    }

    @Test
    void shouldReturnMonoOfListOfAPODWithValidStartDateAndEndDateParametersAreProvided() {
        webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/apod")
                                .queryParam("start_date", "2001-11-23")
                                .queryParam("end_date", "2010-06-03")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(NasaAPOD.class)
                .value(response -> {
                    assertThat(response).hasSameSizeAs(apodList);
                    for (int i = 0; i < response.size(); i++) {
                        assertionForNasaAPODResponse(response.get(i), apodList.get(i), "2001-11-23", "2010-06-03");
                    }
                });
    }


    private void assertionForNasaAPODResponse(NasaAPOD actual, NasaAPOD expected) {
        assertThat(actual.copyright()).isEqualTo(expected.copyright());
        assertThat(actual.date()).isEqualTo(expected.date());
        assertThat(actual.explanation()).isEqualTo(expected.explanation());
        assertThat(actual.mediaUrl()).isEqualTo(expected.mediaUrl());
        assertThat(actual.title()).isEqualTo(expected.title());
    }


    private void assertionForNasaAPODResponse(NasaAPOD actual, NasaAPOD expected, String startDate) {
        assertThat(actual.copyright()).isEqualTo(expected.copyright());
        assertThat(actual.date()).isAfterOrEqualTo(startDate);
        assertThat(actual.explanation()).isEqualTo(expected.explanation());
        assertThat(actual.mediaUrl()).isEqualTo(expected.mediaUrl());
        assertThat(actual.title()).isEqualTo(expected.title());
    }

    private void assertionForNasaAPODResponse(NasaAPOD actual, NasaAPOD expected, String startDate, String endDate) {
        assertThat(actual.copyright()).isEqualTo(expected.copyright());
        assertThat(actual.date()).isBetween(startDate, endDate);
        assertThat(actual.explanation()).isEqualTo(expected.explanation());
        assertThat(actual.mediaUrl()).isEqualTo(expected.mediaUrl());
        assertThat(actual.title()).isEqualTo(expected.title());
    }
}
