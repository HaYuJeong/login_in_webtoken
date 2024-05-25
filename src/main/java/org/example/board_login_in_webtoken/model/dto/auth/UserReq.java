package org.example.board_login_in_webtoken.model.dto.auth;

import lombok.*;

/**
 * packageName : org.example.board_login_in_webtoken.model.dto
 * fileName : UserReq
 * author : hayj6
 * date : 2024-05-22(022)
 * description : todo 프론트에서 전달한 정보를 받을 DTO 객체
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22(022)         hayj6          최초 생성
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    private String userId;    // ID
    private String role; // 권한
    private String password;  // 암호
}
