package org.example.nasa_api.entity;


import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document("nasa_apod")
@Data
@Accessors(chain = true)
public class NasaAPODDocument {

    @Id
    private String id;

    private Date date;

    private String mediaUrl;
}
