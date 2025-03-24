package com.diary.main;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DiaryRepository extends JpaRepository<Diary, String> {

    Page<Diary> findAll(Pageable pageable);


    Optional<Diary> findById(Integer id);
}
