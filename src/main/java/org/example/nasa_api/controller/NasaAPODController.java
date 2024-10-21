package org.example.nasa_api.controller;

import lombok.RequiredArgsConstructor;
import org.example.nasa_api.model.NasaAPOD;
import org.example.nasa_api.service.NasaAPODService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/apod")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class NasaAPODController {

    private final NasaAPODService service;

    @GetMapping
    public Mono<List<NasaAPOD>> getApod(@RequestParam(required = false) String date, @RequestParam(required = false, value = "start_date") String startDate, @RequestParam(required = false, value = "end_date") String endDate, @RequestParam(required = false) Integer count, @RequestParam(required = false) Boolean thumbs) {
        return service.getApod(date, startDate, endDate, count, thumbs);
    }

}
