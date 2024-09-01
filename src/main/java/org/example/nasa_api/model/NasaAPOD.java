package org.example.nasa_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({"copyright", "title", "date", "explanation", "mediaUrl", "mediaType", "version"})
public record NasaAPOD(@JsonProperty("copyright") String copyright, @JsonProperty("date") Date date,
                       @JsonProperty("explanation") String explanation, @JsonProperty("hdurl") String mediaUrl,
                       @JsonProperty("media_type") @JsonIgnore String mediaType,
                       @JsonProperty("service_version") @JsonIgnore String version,
                       @JsonProperty("title") String title) {
}
