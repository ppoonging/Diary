package com.diary.main;


import com.diary.DataNotFoundException;

import com.diary.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;


    public void createDiary(String subject, String content, String weather, LocalDate selectedDate, SiteUser siteUser) {
        Diary d = new Diary();
        d.setAuthor(siteUser);
        d.setSubject(subject);
        d.setContent(content); // 줄바꿈 코드 content.replace("\n", "<br/>"));
        d.setWeather(weather);
        d.setUsername(siteUser.getUsername());
        d.setSelectedDate(selectedDate);
        d.setCreateDateTime(LocalDateTime.now());
        this.diaryRepository.save(d);
    }


    public Page<Diary> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDateTime"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.diaryRepository.findAll(pageable);
    }
    /*내 글 목록*/
    public Page<Diary> getMyList(String username, int page) {
        List<Sort.Order> s = new ArrayList<>();
        s.add(Sort.Order.desc("createDateTime"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(s));
        return this.diaryRepository.findByUsername(username, pageable);
    }

    public Diary getDiary(Integer id) {
        Optional<Diary> d = this.diaryRepository.findById(id);
        if (d.isPresent()) {
            return d.get();
        } else {
            throw new DataNotFoundException("데이터가 없습니다");
        }
    }

    public int weatherScore() {
        List<String> dayWeather = diaryRepository.findTodayWeathers();

        int totalScore = 0;
        for (String weather : dayWeather) {
            switch (weather) {
                case "맑음":
                    totalScore += 2;
                    break;
                case "흐림":
                    totalScore += 0;
                    break;
                case "비":
                    totalScore -= 1;
                    break;
                case "눈":
                    totalScore += 1;
                    break;
                case "바람":
                    totalScore -= 1;
                    break;
            }
        }
        return dayWeather.size() == 0 ? 0 : totalScore / dayWeather.size();

    }

    public int getUserMood(String username) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());
        System.out.println("유저:@@@@@@ " + username);

        List<String> weather = diaryRepository.findWeathersByUserAndMonth(username, start, end);
        return calculateMoodScore(weather);
    }

    public int calculateMoodScore(List<String> weathers) {
        int total = 0;
        int count = 0;
        for (String weather : weathers) {
            if (weather == null) continue;  // null 체크 추가

            switch (weather) {
                case "맑음" -> total += 2;
                case "눈" -> total += 1;
                case "흐림" -> total += 0;
                case "비", "바람" -> total -= 1;
            }
            count++;
        }
        return weathers.isEmpty() ? 0 : total / weathers.size();
    }
}