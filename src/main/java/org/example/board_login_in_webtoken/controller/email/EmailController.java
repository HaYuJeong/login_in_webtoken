package org.example.board_login_in_webtoken.controller.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board_login_in_webtoken.service.user.EmailService;
import org.springframework.web.bind.annotation.*;

/**
 * packageName : org.example.board_login_in_webtoken.controller.user
 * fileName : PasswordResetController
 * author : hayj6
 * date : 2024-05-29(029)
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29(029)         hayj6          최초 생성
 */
@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
// ?는 requestParam, ?이하는 변수를 알아서 받음 / 그냥은 PathVariable, {}가 들어감
    @PutMapping("/sendEmail")
    public String sendEmail(@RequestParam String email, @RequestParam String userId){
        log.debug("이메일"+email);
        log.debug("아이디"+userId);
        emailService.sendSimpleEmail(email, userId);
        return "이메일 발송 성공";
    }
}
