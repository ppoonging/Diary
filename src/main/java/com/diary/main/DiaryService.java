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


    public void createDiary(String subject, String content, LocalDate selectedDate, SiteUser siteUser,String weather) {
        Diary d=new Diary();
        d.setAuthor(siteUser);
        d.setSubject(subject);
        d.setContent(content); // 줄바꿈 코드 content.replace("\n", "<br/>"));
        d.setWeather(weather);
        d.setSelectedDate(selectedDate);
        d.setCreateDateTime(LocalDateTime.now());
        this.diaryRepository.save(d);
    }


    public Page<Diary> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDateTime"));
                Pageable pageable =PageRequest.of(page, 10,Sort.by(sorts));
                return this.diaryRepository.findAll(pageable);
    }
    public Diary getDiary(Integer id) {
        Optional<Diary> d = this.diaryRepository.findById(id);
        if(d.isPresent()){
            return d.get();
        }else{
            throw new DataNotFoundException("데이터가 없습니다");
        }
    }
}
