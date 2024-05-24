package org.example.board_login_in_webtoken.security.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board_login_in_webtoken.model.entity.auth.User;
import org.example.board_login_in_webtoken.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * packageName : org.example.board_login_in_webtoken.security.oauth
 * fileName : SocialLoginServiceCustom
 * author : hayj6
 * date : 2024-05-24(024)
 * description : TODO: 소셜 로그인 클래스 : 카카오 API 인가코드를 받아 인증처리하는 클래스
 *  알고리즘
 *      1) OAuth2UserService : 유저정보 있는 클래스
 *      2) OAuth2 로그인 진행시 키가 되는 필드값(PK 와 같음)
 *      3) 소셜 기본정보 DB 저장, 유저가 있으면 무시
 *      4) 소셜유저 생성 및 내보내기
 * 요약 :
 TODO: 구글 API 인가코드를 받으면 벡엔드에서 새로운 사용자로 DB 에 저장합니다. DB 에 저장시 기본정보로 저장합니다.
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-24(024)         hayj6          최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginServiceCustom implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User>  socialLoginService = new DefaultOAuth2UserService();        // 1) OAuth2UserService : 유저정보 있는 클래스

        OAuth2User socialLogin = socialLoginService.loadUser(userRequest);  // socialLoginService의 loadUser 함수 실행

        Map<String, Object> socialUser = socialLogin.getAttributes();

        String kakaoKey = userRequest.getClientRegistration().getProviderDetails()                                   // 2) OAuth2 로그인 진행시 키가 되는 필드값(PK 와 같음)
                .getUserInfoEndpoint().getUserNameAttributeName();
        saveSocialIdOrSkip(socialUser);                                                                               // 3) 소셜 기본정보 DB 저장, 유저가 있으면 무시
        return new DefaultOAuth2User(                                                                                 // 4) 소셜유저 생성 및 내보내기
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                socialUser,
                kakaoKey);
    }

    //    소셜 기본정보 DB 저장 : DB 에 없으면 저장, 있으면 무시하는 함수
    private void saveSocialIdOrSkip(Map<String, Object> socialUser) {
        try {
            if(userRepository.existsById((String) socialUser.get("id")) == false) {
                userRepository.save(getDefaultUser(socialUser));
            }
        } catch (Exception e) {
            log.debug("saveOrUpdate 에러" ,e.getMessage());
        }
    }
    //  소셜유저를 기본정보로 생성하는 함수
    private User getDefaultUser(Map<String, Object> socialUser) {
        return new User( (String) socialUser.get("id"),                   // 로그인 ID
                encoder.encode("123456234567890"),
                "ROLE_USER"
        );
    }
}
