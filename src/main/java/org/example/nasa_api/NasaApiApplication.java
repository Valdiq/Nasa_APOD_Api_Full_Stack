package org.example.nasa_api;

import org.example.nasa_api.properties.MongoDBProperties;
import org.example.nasa_api.properties.NasaApiProperties;
import org.example.nasa_api.repository.NasaAPODRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({NasaApiProperties.class, MongoDBProperties.class})
@EnableCaching
@EnableMongoRepositories
public class NasaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NasaApiApplication.class, args);
    }

}
