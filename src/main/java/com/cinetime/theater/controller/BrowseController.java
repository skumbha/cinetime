package com.cinetime.theater.controller;


import com.cinetime.theater.dto.TheaterShowResponse;
import com.cinetime.theater.service.TheaterService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BrowseController {

    private final TheaterService theaterService;

    public BrowseController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping("/v1/browse")
    public ResponseEntity<List<TheaterShowResponse>> browseTheaters(
            @RequestParam("movie_id") Long movieId,
            @RequestParam String city,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        List<TheaterShowResponse> theaterResponses = theaterService.browseTheaters(movieId, city, date.atStartOfDay());
        return ResponseEntity.ok(theaterResponses);
    }
}