package org.example.board_login_in_webtoken.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board_login_in_webtoken.model.entity.auth.User;
import org.example.board_login_in_webtoken.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

/**
 * packageName : org.example.board_login_in_webtoken.service.user
 * fileName : MyRandomService
 * author : hayj6
 * date : 2024-05-29(029)
 * description : todo : 숫자+문자 조합 난수 코드 보내기
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29(029)         hayj6          최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyRandomService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 알파벳 소문자, 대문자, 숫자를 포함한 문자열
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static SecureRandom random = new SecureRandom();

    public String generateTemporaryPassword(String userId) {

        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomNum = CHARACTERS.charAt(index);
            sb.append(randomNum);
        }
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        optionalUser.ifPresent(user -> {user.setPassword(passwordEncoder.encode(sb.toString()));
        userRepository.save(user);
        });
        return sb.toString();
    }
}
