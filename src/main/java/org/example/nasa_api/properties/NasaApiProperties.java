package org.example.nasa_api.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "nasa-api")
public record NasaApiProperties(@NotNull String apiKey, @NotNull String baseUrl, @NotNull String baseApodUrl) {
}
