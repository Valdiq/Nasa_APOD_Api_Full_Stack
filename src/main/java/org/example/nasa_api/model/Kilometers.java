package org.example.nasa_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Kilometers(@JsonProperty("estimated_diameter_min") double minDiameter,
                         @JsonProperty("estimated_diameter_max") double maxDiameter) {
}
