package org.example.nasa_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CloseApproachData(@JsonProperty("miss_distance") MissDistance missDistance,
                                @JsonProperty("orbiting_body") String orbitingBody) {
}
