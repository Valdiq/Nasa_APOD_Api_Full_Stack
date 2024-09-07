package org.example.nasa_api.controller;

import lombok.RequiredArgsConstructor;
import org.example.nasa_api.model.NasaNeoWs;
import org.example.nasa_api.model.NearEarthObject;
import org.example.nasa_api.service.NasaNeoWsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/neows")
@RequiredArgsConstructor
public class NasaNeoWsController {

    private final NasaNeoWsService service;

    @GetMapping("/feed")
    public Mono<NasaNeoWs> getNeoWs(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        return service.getNeows(startDate, endDate);
    }

    @GetMapping("/neo/{asteroidId}")
    public Mono<NearEarthObject> getAsteroidById(@PathVariable int asteroidId) {
        return service.getAsteroidById(asteroidId);
    }

}
