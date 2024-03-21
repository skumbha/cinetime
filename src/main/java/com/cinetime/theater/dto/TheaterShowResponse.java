package com.cinetime.theater.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class TheaterShowResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long theaterId;
    private String theaterName;
    private List<ShowInfo> shows;

    // Getters and setters
}