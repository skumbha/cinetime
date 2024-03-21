package com.cinetime.theater.repository;


import com.cinetime.theater.dto.ShowDTO;
import com.cinetime.theater.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query("SELECT new com.cinetime.theater.dto.ShowDTO(T.theaterId, T.name, S.screenId, S.screenType, SH.showId, SH.startTime) " +
            "FROM Theater T " +
            "JOIN Screen S ON T.theaterId = S.theaterId " +
            "JOIN Show SH ON S.screenId = SH.screenId " +
            "JOIN Movie M ON SH.movieId = M.movieId " +
            "WHERE M.movieId = :movieId " +
            "AND T.city = :city " +
            "AND FUNCTION('DATE', SH.startTime) = FUNCTION('DATE', :date)")
    List<ShowDTO> findShowsByMovieIdAndCityAndDate(Long movieId, String city, LocalDateTime date);
}
