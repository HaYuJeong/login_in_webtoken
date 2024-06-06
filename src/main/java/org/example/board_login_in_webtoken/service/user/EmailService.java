package org.example.board_login_in_webtoken.service.user;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.board_login_in_webtoken.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.board_login_in_webtoken.service.user
 * fileName : EmailService
 * author : hayj6
 * date : 2024-05-29(029)
 * description : todo : 이메일 서비스 구현
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29(029)         hayj6          최초 생성
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

//    todo : 자바에서 제공하는 클래스
    private final JavaMailSender mailSender;

//    todo : 숫자+문자 조합 난수
    private final MyRandomService myRandomService;

    public void sendSimpleEmail(String email, String userId) {
        String randomNumber = myRandomService.generateTemporaryPassword(userId);
        String text = "임시 비밀번호 : ";
        String subject = "임시 비밀번호 발급해드립니다.";
        String modifiedText = text + randomNumber;
        log.debug("1");

        MimeMessage mimeMessage = mailSender.createMimeMessage();   // 자바에서 제공 : MimeMessage 객체 : 전송하는 객체
        log.debug("2");
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
            helper.setTo(email);
            helper.setSubject(subject);
            log.debug("3");
            helper.setText(modifiedText, true);
            log.debug("3.5" + mimeMessage);
            mailSender.send(mimeMessage);
            log.debug("4");
//            mimeMessage.setSubject(subject, "utf-8");
//            mimeMessage.setText(modifiedText, "utf-8", "html");
//            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//            mailSender.send(mimeMessage);


        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new RuntimeException("이메일 전송에 실패하였습니다." + e.getMessage());
        }
    }
}
