package org.example.nasa_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EstimatedDiameter(@JsonProperty("kilometers") Kilometers kilometers) {
}
