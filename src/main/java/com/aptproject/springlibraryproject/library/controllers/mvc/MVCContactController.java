package com.aptproject.springlibraryproject.library.controllers.mvc;

import com.aptproject.springlibraryproject.library.constants.MailConstants;
import com.aptproject.springlibraryproject.library.dto.ContactDTO;
import com.aptproject.springlibraryproject.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/contact")
public class MVCContactController {

    private final JavaMailSender javaMailSender;

    public MVCContactController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @GetMapping()
    public String contact() {
        return "contact";
    }

    @PostMapping
    public String sendEmail(@ModelAttribute("contactForm")ContactDTO contactDTO) {
        SimpleMailMessage mailMessage = MailUtils.createMailMessage(
                "spring.project.42@mail.ru",
                MailConstants.MAIL_SUBJECT_CONTACT,
                MailConstants.MAIL_MESSAGE_CONTACT + contactDTO.getFromEmail() + "\n" +
                        "Сообщение: " + contactDTO.getBody() + "\n" +
                        "Телефон: " + contactDTO.getPhone()
        );
        javaMailSender.send(mailMessage);
        log.info("Сообщение успешно отправлено: {}", contactDTO);
        return "redirect:/";

    }
}