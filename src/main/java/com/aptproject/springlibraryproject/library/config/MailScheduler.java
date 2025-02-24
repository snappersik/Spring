package com.aptproject.springlibraryproject.library.config;

import com.aptproject.springlibraryproject.library.service.UserService;
import com.aptproject.springlibraryproject.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Component
@Slf4j
public class MailScheduler {

    private final UserService userService;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailScheduler(UserService userService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    // Крон на каждую минуту: "0 0/1 * 1/1 * *"
    // Каждый день в 6 утра: "0 0 6 * * ?"
    @Scheduled(cron = "0 0 6 * * ?")
    public void sentMailsToDebtors() {
        log.info("Запуск планировщика по проверки должников…");
        List<String> emails = userService.getUserEmailsWithDelayedRentDate();

        if (!emails.isEmpty()) {
            SimpleMailMessage simpleMailMessage = MailUtils.createMailMessage(
                    emails,
                    "Напоминание о просрочке возврата книг(и)",
                    "Вы - злостный нарушитель!!! Верните книгу!!"
            );
            javaMailSender.send(simpleMailMessage);
        }
    }
}

