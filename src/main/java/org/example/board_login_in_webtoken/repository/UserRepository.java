package org.example.board_login_in_webtoken.repository;

import org.example.board_login_in_webtoken.model.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * packageName : org.example.board_login_in_webtoken.repository
 * fileName : UserRepository
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
@Repository
public interface UserRepository extends JpaRepository<User, String> {

//    todo 아이디 찾기
    @Query(value = "SELECT * FROM LOTTO_USER\n" +
            "WHERE USER_ID = :userId"
            ,nativeQuery = true)
    public Optional<User> findByUserId(@Param("userId") String userId);


//    todo 아이디 찾기를 위해 아래 변수를 받아서 일치하는 user반환
    @Query(value = "SELECT * FROM LOTTO_USER WHERE ROLE = :role AND USER_NAME = :userName AND PHONE_NUM = :phoneNum"
            , nativeQuery = true)
    public User findId(@Param("role") String role, @Param("userName") String userName, @Param("phoneNum") String phoneNum);


//    todo 비밀번호 찾기 위한 회원 조회
    @Query(value = "SELECT * FROM LOTTO_USER WHERE ROLE = :role AND USER_ID= :userId AND PW_QUESTION = :pwQuestion AND PW_ANSWER = :pwAnswer"
            , nativeQuery = true)
    public User getForPw(@Param("role") String role, @Param("userId") String userId, @Param("pwQuestion") String pwQuestion, @Param("pwAnswer") String pwAnswer);


//    todo userId로 조회해서 새 비밀번호로 수정
    @Transactional
    @Modifying // 업데이트문을 쿼리문으로 작성할때는 두개의 어노테이션을 추가해줘야한다.
    @Query(value = "UPDATE LOTTO_USER SET PASSWORD = :newPw WHERE USER_ID = :userId "
            ,nativeQuery = true)
    public void updatePw(@Param("newPw") String newPw, @Param("userId") String userId);


    //  todo 회원 정보 수정 함수
    @Transactional
    @Modifying // 업데이트문을 쿼리문으로 작성할때는 두개의 어노테이션을 추가해줘야한다.
    @Query(value = "UPDATE LOTTO_USER \n" +
            "SET USER_NAME = :userName\n" +
            ",BIRTHDAY = :birthday\n" +
            ",PHONE_NUM = :phoneNum\n" +
            ",EMAIL = :email\n" +
            ",DEPARTMENT = :department\n"+
            ",NORMAL_ADDRESS = :normalAddress\n" +
            ",DETAIL_ADDRESS = :detailAddress\n" +
            "WHERE USER_ID = :userId "
            ,nativeQuery = true)
    public void updateUserById(
            @Param("userName") String userName
            , @Param("birthday") long birthday
            , @Param("phoneNum") String phoneNum
            , @Param("email") String email
            , @Param("department") String department
            , @Param("normalAddress") String normalAddress
            , @Param("detailAddress") String detailAddress
            , @Param("userId") String userId
    );
}
