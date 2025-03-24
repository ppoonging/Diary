package com.diary.user;


import com.diary.DataNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteUserSerevice {
    private final SiteUserRepository diaryUserRepository;
    private final PasswordEncoder passwordEncoder;


    public SiteUser create( String username, String password
            ,String myName, String email, String phoneNumber){
        SiteUser dU= new SiteUser();
        dU.setUsername(username);
        dU.setPassword(passwordEncoder.encode(password) );
        dU.setMyName(myName);
        dU.setEmail(email);
        dU.setPhoneNumber(phoneNumber);
        this.diaryUserRepository.save(dU);
        return dU;
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> diaryUser = this.diaryUserRepository.findByUsername(username);
        if(diaryUser.isPresent()){
            return diaryUser.get();
        }else{
            throw new DataNotFoundException("데이터 없음");
        }

    }


}
