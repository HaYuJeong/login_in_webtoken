package org.example.board_login_in_webtoken.model.dto.auth;

import lombok.*;

/**
 * packageName : org.example.board_login_in_webtoken.model.dto
 * fileName : UserRes
 * author : hayj6
 * date : 2024-05-22(022)
 * description :
 * 요약 :
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
    //    jwt(웹토큰), 이메일, 권한명 => 프론트에 전송
    private String accessToken;   // 웹토큰

    //    토큰 타입
    private String tokenType = "Bearer"; // 토큰 형태(고정)
    private String userId;          // 이메일
    private String role;       // 권한명

    //    생성자 : alt + insert (3개)
    public UserRes(String accessToken, String userId, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.role = role;
    }
}
