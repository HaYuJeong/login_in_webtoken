package org.example.board_login_in_webtoken.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board_login_in_webtoken.model.entity.auth.User;
import org.example.board_login_in_webtoken.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName : org.example.board_login_in_webtoken.controller.user
 * fileName : UserController
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
@RestController
@Slf4j
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService ;

    // todo : 회원 상세조회 : 회원정보 수정

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findById(@PathVariable String userId){
        try{
            User user = userService.findById(userId).get();
//            User user = userService.findById(userId);
//            if (user.isEmpty() == false) {
            return new ResponseEntity<>(user, HttpStatus.OK);
//            }
//            else {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
        } catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // todo  회원 정보 수정 함수 : 1. 회원정보수정 페이지에서 사용
    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId,
                                             @RequestBody User user){
        try {
            userService.updateUserById(user.getUserName(), user.getBirthday(),user.getPhoneNum(), user.getEmail(), user.getDepartment(), user.getNormalAddress(), user.getDetailAddress(), user.getUserId());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // todo  회원 소프트 삭제 함수 : 1. 회원 탈퇴
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable String userId){
        try {
//            log.debug(userId);
            userService.removeById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
