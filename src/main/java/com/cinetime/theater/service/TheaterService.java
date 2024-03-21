package com.cinetime.theater.service;

import com.cinetime.theater.dto.ShowDTO;
import com.cinetime.theater.dto.ShowInfo;
import com.cinetime.theater.dto.TheaterShowResponse;
import com.cinetime.theater.exception.ServiceRuntimeException;
import com.cinetime.theater.repository.TheaterRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;


    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;

    }

    @Cacheable(value = "theaters", key = "{#movieId, #city, #date}" , unless = "T(com.cinetime.theater.config.RedisUtil).isCacheUnavailable()")
    public List<TheaterShowResponse> browseTheaters(Long movieId, String city, LocalDateTime date) {

        if(movieId == null || city == null || date == null){
            throw new ServiceRuntimeException("Invalid input");
        }

         return convertToShowResponseList(theaterRepository.findShowsByMovieIdAndCityAndDate(movieId,city,date))      ;

    }


    public List<TheaterShowResponse> convertToShowResponseList(List<ShowDTO> showDTOList) {

        if(showDTOList==null){
            return new ArrayList<>();
        }
        // Group the ShowDTO list by theaterId
        Map<Long, List<ShowDTO>> groupedByTheater = showDTOList.stream()
                .collect(Collectors.groupingBy(ShowDTO::getTheaterId));

        // Convert each group into a TheaterShowResponse object
        List<TheaterShowResponse> theaterShowResponses = groupedByTheater.entrySet().stream()
                .map(entry -> {
                    TheaterShowResponse theaterShowResponse = new TheaterShowResponse();
                    theaterShowResponse.setTheaterId(entry.getKey());
                    theaterShowResponse.setTheaterName(entry.getValue().get(0).getTheaterName());

                    // Convert the list of ShowDTOs to a list of ShowInfo objects
                    List<ShowInfo> showInfos = entry.getValue().stream()
                            .map(showDTO -> {
                                ShowInfo showInfo = new ShowInfo();
                                showInfo.setShowId(String.valueOf(showDTO.getShowId()));
                                showInfo.setShowTime(showDTO.getShowTime().toString()); // Convert LocalDateTime to String as needed
                                showInfo.setScreenId(showDTO.getScreenId().intValue());
                                showInfo.setScreenType(showDTO.getScreenType());
                                return showInfo;
                            })
                            .collect(Collectors.toList());

                    theaterShowResponse.setShows(showInfos);
                    return theaterShowResponse;
                })
                .collect(Collectors.toList());

        return theaterShowResponses;
    }

}
