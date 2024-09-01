package org.example.nasa_api.repository;

import org.example.nasa_api.entity.NasaAPODDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NasaAPODRepository extends MongoRepository<NasaAPODDocument, Long> {
}
