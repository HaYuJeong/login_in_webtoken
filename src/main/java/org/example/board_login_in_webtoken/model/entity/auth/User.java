package org.example.board_login_in_webtoken.model.entity.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.example.board_login_in_webtoken.model.common.BaseTimeEntity2;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * packageName : org.example.boardbackend.model.entity.auth
 * fileName : User
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
@Entity
@Table(name="USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// soft delete jpa 어노테이션
@Where(clause = "WITHDRAW_YN = 'N'")
@SQLDelete(sql ="UPDATE USER SET WITHDRAW_YN = 'N', WITHDRAW_TIME=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE USER_ID = ?")
public class User extends BaseTimeEntity2 {

    @Id
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

    public User(String userId, String password, String userName, long birthday, String phoneNum, String email, String role, String department, String normalAddress, String detailAddress, String pwQuestion, String pwAnswer) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.birthday = birthday;
        this.phoneNum = phoneNum;
        this.email = email;
        this.role = role;
        this.department = department;
        this.normalAddress = normalAddress;
        this.detailAddress = detailAddress;
        this.pwQuestion = pwQuestion;
        this.pwAnswer = pwAnswer;
    }
}
