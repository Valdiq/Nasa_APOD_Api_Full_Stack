package org.example.nasa_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record NasaNeoWs(@JsonProperty("near_earth_objects") Map<String, List<NearEarthObject>> nearEarthObjects) {
    public NasaNeoWs {
        nearEarthObjects = Map.copyOf(nearEarthObjects);
    }

    @Override
    public Map<String, List<NearEarthObject>> nearEarthObjects() {
        return Map.copyOf(nearEarthObjects);
    }


}
