package com.cinetime.theater.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
public class ShowInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String showId;
    private String showTime;
    private int screenId;
    private String screenType;

    // Getters and setters
}