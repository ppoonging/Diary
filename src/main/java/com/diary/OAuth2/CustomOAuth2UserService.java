package com.diary.OAuth2;

import com.diary.user.SiteUser;
import com.diary.user.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SiteUserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 네이버는 response 내부에 유저 정보가 있음
        Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");

        String email = (String) response.get("email");

        String name = (String) response.get("name");

        SiteUser user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(new SiteUser(email, name, email, "naver")));
        // 세션에 직접 저장하고 싶으면 여기서 가능
        // ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().setAttribute("loginUser", user);

        // SecurityContext에서 사용자 정보를 사용할 수 있도록 리턴
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                response, "email");
    }
}
