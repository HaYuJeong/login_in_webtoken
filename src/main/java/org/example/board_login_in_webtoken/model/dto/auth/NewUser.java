package org.example.board_login_in_webtoken.model.dto.auth;

import lombok.*;

/**
 * packageName : org.example.board_login_in_webtoken.model.dto.auth
 * fileName : NewUser
 * author : hayj6
 * date : 2024-05-22(022)
 * description : TODO 회원가입 용 DTO, passwordEncoder 를 해주기위해 만들었다.
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
public class NewUser {
    private String userId;
    private String password;
    private String userName;
    private long birthday;
    private String phoneNum;
    private String email;
    private String role;
    private String department;
    private String normalAddress;
    private String detailAddress;
    private String pwUpdateTime;
    private String pwQuestion;
    private String pwAnswer;
}
