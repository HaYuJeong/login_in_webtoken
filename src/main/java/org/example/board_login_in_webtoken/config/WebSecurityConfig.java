package org.example.board_login_in_webtoken.config;

import lombok.RequiredArgsConstructor;
import org.example.board_login_in_webtoken.security.oauth.SocialLoginServiceCustom;
import org.example.board_login_in_webtoken.security.oauth.SocialLoginSuccess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.example.board_login_in_webtoken.security.jwt.AuthTokenFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * packageName : org.example.boardbackend.config
 * fileName : WebSecurityConfig
 * author : hayj6
 * date : 2024-05-21(021)
 * description :
 *  1) DB 인증을 위한 함수    : passwordEncoder()
 *  2) 패스워드 암호화 함수     : 필수 정의
 *    @Bean : IOC (스프링이 객체를 생성해주는 것), 함수의 리턴객체를 생성함
 *      => (참고) 용어 : 스프링 생성한 객체 == 빈(Bean==콩)
 *  3) JWT 웹토큰 자동인증 함수 : authenticationJwtTokenFilter()
 *  4) img, css, js 등 인증 무시 설정 함수 : webSecurityCustomizer()
 *      => 사용법 : (web) -> web.ignoring().requestMatchers("경로", "경로2"...)
 *  5) 스프링 시큐리티 규칙 정의 함수(***) : filterChain(HttpSecurity http)
 *    5-1) cors 사용
 *    5-2) csrf 해킹 보안 비활성화(쿠키/세션 사용않함)
 *    5-3) 쿠키/세션 안함(비활성화) -> 로컬스토리지/웹토큰
 *    5-4) form 태그 action 을 이용한 로그인 사용않함 -> axios 통신함
 *    5-5) /api/auth/**  : 이 url 은 모든 사용자 접근 허용, ** (하위 url 모두 포함)
 *    5-8) / : 이 url 은 모든 사용자 접근 허용
 *    5-9) 웹토큰 클래스를 스프링시큐리티 설정에 끼워넣기 : 모든 게시판 조회(CRUD)에서 아래 인증을 실행함
 *  6) TODO: 카카오 소셜 로그인
 *    6-1) 소셜 로그인 성공후 처리할 리다이렉션해서 카카오 인가코드 받음
 *    6-2) 카카오 인가코드 확인 후에 DB 인증, 웹토큰 발행, 프론트로 전송
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-21(021)         hayj6          최초 생성
 */
@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {
    //  TODO: 1) 여기는 DB 인증을 하는 클래스


//    TODO: 6) 카카오 소셜 로그인
    private final SocialLoginSuccess socialLoginSuccess;    // todo : 성공 후에 실행 될 클래스, vue로 웹토큰, 유저정보를 전송

    private final SocialLoginServiceCustom socialLoginServiceCustom;   // todo : 카카오 인가코드를 받으면 실행될 클래스, DB에 새로운 소셜 사용자 등록

    //  TODO: 2) 패스워드 암호화 함수     : 필수 정의
//        @Bean : IOC (스프링이 객체를 생성해주는 것), 함수의 리턴객체를 생성함
//         => (참고) 용어 : 스프링 생성한 객체 == 빈(Bean==콩)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    TODO: 3) JWT(웹토큰) 객체(자동인증을 위한 필터) 정의
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(); // 개발자가 작성한 웹토큰 인증필터 생성자 함수
    }

    //    TODO: 4) 공통 jsp, img, css, js 등 : 인증 안받는 것들은 무시하도록 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
//      사용법 : (web) -> web.ignoring().requestMatchers("경로", "경로2"...)
        return (web) -> web.ignoring().requestMatchers(
                "/img/**",
                "/css/**",
                "/js/**"
        );
    }

    //    TODO: 5) 스프링 시큐리티 규칙 정의 함수(***) : JWT(웹토큰) 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults()); // cors 사용
        http.csrf((csrf) -> csrf.disable());   // csrf 해킹 보안 비활성화(쿠키/세션 사용않함)
//        쿠키/세션 안함(비활성화) -> 로컬스토리지/웹토큰
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(req -> req.disable()); // form 태그 action 을 이용한 로그인 사용않함 -> axios 통신함

        http.authorizeHttpRequests(req -> req // todo 여기서 부터 controller의 url을 제한함으로 db와의 접근을 제한한다.
//                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/api/auth/**").permitAll()       // 로그인 및 회원가입 함수
                .requestMatchers("/api/user/**").hasRole("USER")       // user관련 모든 함수
                .requestMatchers("/api/admin/**").hasRole("ADMIN")       // 관리자의 모든 함수
                .requestMatchers("/api/normal/**").permitAll()       // 관리자의 모든 함수

                .anyRequest()
//                .authenticated());
                .permitAll());
//

//        TODO: 6) 카카오 소셜 로그인 설정 부분
        http.oauth2Login(req -> req
                .successHandler(socialLoginSuccess)                                    // TODO: 6-1) 소셜 로그인 성공후 처리할 리다이렉션해서 구글 인가코드 받음
                .userInfoEndpoint(arg -> arg.userService(socialLoginServiceCustom))    // TODO: 6-2) 구글 인가코드 확인 후에 DB 인증, 웹토큰 발행, 프론트로 전송
        );


//        TODO: 5-9) 웹토큰 클래스를 스프링시큐리티 설정에 끼워넣기 : 모든 조회(CRUD)에서 아래 인증을 실행함
//         웹토큰 인증필터를 UsernamePasswordAuthenticationFilter(id/암호 인증필터) 앞에 끼워넣기
//         AUTHTOKENFILTER가 자동으로 실행되게함
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
