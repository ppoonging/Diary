package com.diary.mbti;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MbtiRepository extends JpaRepository<Mbti,Integer> {

    Optional<Mbti> findByMaleMbtiAndFemaleMbti(String maleMbti, String femaleMbti);

}
