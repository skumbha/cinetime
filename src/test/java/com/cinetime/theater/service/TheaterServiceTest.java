package com.cinetime.theater.service;

import com.cinetime.theater.dto.ShowDTO;
import com.cinetime.theater.dto.ShowInfo;
import com.cinetime.theater.dto.TheaterShowResponse;
import com.cinetime.theater.repository.TheaterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TheaterServiceTest {

    @Mock
    private TheaterRepository theaterRepository;

    @InjectMocks
    private TheaterService theaterService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertToShowResponseList_PositiveScenario() {
        // Setup mock behavior for theaterRepository
        List<ShowDTO> mockShowDTOList = new ArrayList<>();
        ShowDTO mockShowDTO = new ShowDTO();
        mockShowDTO.setTheaterId(1L);
        mockShowDTO.setTheaterName("Mock Theater");
        mockShowDTO.setShowId(1L);
        mockShowDTO.setShowTime(LocalDateTime.now());
        mockShowDTO.setScreenId(1L);
        mockShowDTO.setScreenType("Standard");
        mockShowDTOList.add(mockShowDTO);

        Mockito.when(theaterRepository.findShowsByMovieIdAndCityAndDate(Mockito.anyLong(), Mockito.anyString(), Mockito.any(LocalDateTime.class)))
                .thenReturn(mockShowDTOList);

        // Call the method to be tested
        List<TheaterShowResponse> theaterShowResponses = theaterService.browseTheaters(123L, "Pune", LocalDateTime.now());

        // Assert the result
        Assertions.assertNotNull(theaterShowResponses);
        Assertions.assertEquals(1, theaterShowResponses.size());

        TheaterShowResponse theaterShowResponse = theaterShowResponses.get(0);
        Assertions.assertEquals(1L, theaterShowResponse.getTheaterId());
        Assertions.assertEquals("Mock Theater", theaterShowResponse.getTheaterName());
        Assertions.assertEquals(1, theaterShowResponse.getShows().size());

        ShowInfo showInfo = theaterShowResponse.getShows().get(0);
        Assertions.assertEquals("1", showInfo.getShowId());
        Assertions.assertNotNull(showInfo.getShowTime());
        Assertions.assertEquals(1, showInfo.getScreenId());
        Assertions.assertEquals("Standard", showInfo.getScreenType());
    }

    @Test
    public void testConvertToShowResponseList_EmptyResult() {
        // Setup mock behavior for theaterRepository
        Mockito.when(theaterRepository.findShowsByMovieIdAndCityAndDate(Mockito.anyLong(), Mockito.anyString(), Mockito.any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>()); // Empty list

        // Call the method to be tested
        List<TheaterShowResponse> theaterShowResponses = theaterService.browseTheaters(123L, "Pune", LocalDateTime.now());

        // Assert the result
        Assertions.assertNotNull(theaterShowResponses);
        Assertions.assertTrue(theaterShowResponses.isEmpty());
    }

    @Test
    public void testConvertToShowResponseList_NullInput() {
        // Call the method to be tested with null input
        List<TheaterShowResponse> theaterShowResponses = theaterService.convertToShowResponseList(null);

        // Assert the result
        Assertions.assertNotNull(theaterShowResponses);
        Assertions.assertTrue(theaterShowResponses.isEmpty());
    }


}
