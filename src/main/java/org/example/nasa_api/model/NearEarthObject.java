package org.example.nasa_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record NearEarthObject(@JsonProperty("id") String id, @JsonProperty("name") String name,
                              @JsonProperty("estimated_diameter") EstimatedDiameter estimatedDiameter,
                              @JsonProperty("is_potentially_hazardous_asteroid") boolean isHazardous,
                              @JsonProperty("close_approach_data") List<CloseApproachData> closeApproachData) {

    public NearEarthObject {
        if (closeApproachData == null) {
            closeApproachData = List.of();
        } else {
            closeApproachData = List.copyOf(closeApproachData);
        }
    }

    @Override
    public List<CloseApproachData> closeApproachData() {
        return List.copyOf(closeApproachData);
    }
}
