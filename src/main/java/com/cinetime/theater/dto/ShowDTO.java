package com.cinetime.theater.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class ShowDTO {

    private Long theaterId;

    private String theaterName;

    private Long screenId;

    private String screenType;

    private Long showId;

    private LocalDateTime showTime;

}
