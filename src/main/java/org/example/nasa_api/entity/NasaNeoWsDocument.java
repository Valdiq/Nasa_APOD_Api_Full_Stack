package org.example.nasa_api.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.nasa_api.model.NasaNeoWs;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("nasa-neows")
@Data
@Accessors(chain = true)
public class NasaNeoWsDocument {

    @Id
    private String id;

    private NasaNeoWs neoWsData;
}
