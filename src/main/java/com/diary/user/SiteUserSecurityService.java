package com.diary.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteUserSecurityService implements UserDetailsService {

    private final SiteUserRepository diaryUserRepository;
    //UserDetailssms는 가장 큰 씨큐리티
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _diary = this.diaryUserRepository.findByUsername(username);//_안 넣어도 됨

        if (_diary.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }
        SiteUser diary = _diary.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(SiteUserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(SiteUserRole.USER.getValue()));
        }
        return new User(diary.getUsername(), diary.getPassword(), authorities);
        // 둘다 아니면 새로운 유저로 만들자(회원가입이 가능햐짐)
    }


}
