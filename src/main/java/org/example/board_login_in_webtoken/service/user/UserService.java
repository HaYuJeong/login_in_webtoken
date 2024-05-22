package org.example.board_login_in_webtoken.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board_login_in_webtoken.model.entity.auth.User;
import org.example.board_login_in_webtoken.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : org.example.board_login_in_webtoken.service.auth
 * fileName : UserService
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
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //    todo 회원 있는지 확인 함수 : 회원가입
    public boolean existsById(String userId) {
        boolean result = userRepository.existsById(userId);

        return result;
    }

    //    todo 저장 : 회원가입
    public User save(User user) {
        User user2 = userRepository.save(user);

        return user2;
    }

    //    TODO: userID 상세 조회
    public Optional<User> findById(String userId){
        Optional<User> user = userRepository.findById(userId);
        return user;
    }

    // todo : id찾는 함수
    public User findId(String role, String userName, String phoneNum){
        User user = userRepository.findId(role, userName, phoneNum);
        return user;
    }

    // todo 비밀번호 찾기위해 회원 확인 함수
    public boolean getForPw(String role, String userId, String pwQuestion, String pwAnswer){
        User user = userRepository.getForPw(role, userId, pwQuestion, pwAnswer);
        if (user != null) {
            return true;
        }else {
            return false;
        }
    }

    // todo 새로운 비밀번호 update함수
    public void updatePw(String newPw, String userId){

        userRepository.updatePw(newPw, userId);
    }

//    todo 회원정보 수정 함수
    public void updateUserById(String userName
            ,  long birthday
            ,  String phoneNum
            ,  String email
            ,  String normalAddress
            ,  String detailAddress
            ,  String userId){
        userRepository.updateUserById( userName
                ,   birthday
                ,   phoneNum
                ,   email
                ,   normalAddress
                ,   detailAddress
                ,   userId);
    }

    // todo 유저 삭제 함수 (소프트 삭제)
    public boolean removeById(String userId){
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }else {
            return false;
        }
    }
}
