package org.example.nasa_api.repository;

import org.example.nasa_api.entity.NasaNeoWsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NasaNeoWsRepository extends MongoRepository<NasaNeoWsDocument, String> {
}
