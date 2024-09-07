package org.example.nasa_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MissDistance(@JsonProperty("kilometers") String kilometers) {
}
