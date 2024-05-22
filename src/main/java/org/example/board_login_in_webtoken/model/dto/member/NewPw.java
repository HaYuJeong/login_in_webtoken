package org.example.board_login_in_webtoken.model.dto.member;

import lombok.*;

/**
 * packageName : org.example.board_login_in_webtoken.model.dto.member
 * fileName : NewPw
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
public class NewPw {
    private String userId;
    private String newPw;
}
