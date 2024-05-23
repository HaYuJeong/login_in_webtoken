package org.example.board_login_in_webtoken.model.dto.auth;

import lombok.*;

/**
 * packageName : org.example.board_login_in_webtoken.model.dto
 * fileName : UserRes
 * author : hayj6
 * date : 2024-05-22(022)
 * description :
 * 요약 : JWT 토큰 : 로그인 / 웹 서핑할 때 사용
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22(022)         hayj6          최초 생성
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRes {
//    아이디, 권한, 웹토큰 보냄. 여기는 암호화되어있어서 모르니까 부가정보를 같이 보냄.
    //    jwt(웹토큰), 이메일, 권한명 => 프론트에 전송
    private String accessToken;   // 웹토큰

    //    토큰 타입
    private String tokenType = "Bearer"; // 토큰 형태(고정) 네트워크상으로 통신할떄는 이렇게 적기
    private String userId;          // 이메일
    private String role;       // 권한명

    //    생성자 : alt + insert (3개)
    public UserRes(String accessToken, String userId, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.role = role;
    }
}
