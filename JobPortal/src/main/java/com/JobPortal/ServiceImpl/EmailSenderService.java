package com.JobPortal.ServiceImpl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    private static int otp = UserServiceImpl.otp;

    public void sendEmail(String email, String subject, String body, int randomNumber) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("dark55thor@gmail.com");
            message.setTo(email);
            message.setText(body);
            message.setSubject(subject);

            mailSender.send(message);
            System.out.println("Mail sent successfully." + otp);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}