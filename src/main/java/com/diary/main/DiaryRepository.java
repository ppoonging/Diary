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
    //DATE(d.createDateTime) ← 이건 MySQL 문법
    //Oracle에서는 DATE() 함수 안 씀 → TRUNC(...)를 써야 해
    @Query(value = "SELECT weather FROM diary WHERE TRUNC(create_date_time) = TRUNC(SYSDATE)", nativeQuery = true)
    List<String> findTodayWeathers();


    /*로그인한 유져*/
    @Query("SELECT d.weather FROM Diary d WHERE d.username = :username AND d.selectedDate BETWEEN :start AND :end")
    List<String> findWeathersByUserAndMonth(@Param("username") String username,
                                            @Param("start") LocalDate start,
                                            @Param("end") LocalDate end);

    /*모든 유저 기분지수*/
    @Query("SELECT d.weather FROM Diary d WHERE d.selectedDate BETWEEN :start AND :end")
    List<String> findWeathersByMonth(@Param("start") LocalDate start,
                                     @Param("end") LocalDate end);

}
