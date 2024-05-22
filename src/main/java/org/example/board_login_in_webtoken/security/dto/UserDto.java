package org.example.board_login_in_webtoken.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.example.board_login_in_webtoken.model.entity.auth.User;

import java.util.Collection;

/**
 * packageName : org.example.boardbackend.security.dto
 * fileName : UserDto
 * author : hayj6
 * date : 2024-05-21(021)
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-21(021)         hayj6          최초 생성
 */
@Setter
@Getter
@ToString
public class UserDto extends User {

    // 개발자 추가 속성(필드) : userId
    private String userId; // 로그인 ID == username

    //    생성자 : 자동생성 : alt + insert(cmd + n)

    public UserDto(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userId, password, authorities);
        this.userId = userId;
    }
}
