package org.example.nasa_api.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "mongodb")
public record MongoDBProperties(@NotNull String database, @NotNull String uri) {
}
