package com.diary.main;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary, String> {

    Page<Diary> findAll(Pageable pageable);

    Page<Diary> findByUsername(String username, Pageable pageable);



    Optional<Diary> findById(Integer id);

    /*오늘 기분지수*/
    @Query("SELECT d.weather FROM Diary d WHERE DATE(d.createDateTime) = CURRENT_DATE")
    List<String> findTodayWeathers();

    /*로그인한 유져*/
    @Query("SELECT d.weather FROM Diary d WHERE d.username = :username AND d.selectedDate BETWEEN :start AND :end")
    List<String> findWeathersByUserAndMonth(@Param("username") String username,
                                            @Param("start") LocalDate start,
                                            @Param("end") LocalDate end);


}
