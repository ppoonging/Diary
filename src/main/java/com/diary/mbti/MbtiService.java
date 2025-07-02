package com.diary.mbti;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MbtiService {

    private final MbtiRepository mbtiRepository;

    public Mbti getCompatibility(String maleMbti, String femaleMbti) {
        return mbtiRepository.findByMaleMbtiAndFemaleMbti(maleMbti,femaleMbti)
                .orElse(null);




    }
}
