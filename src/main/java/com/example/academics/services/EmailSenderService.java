package com.example.academics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public void senderMail(String toMail, String bookName, String description, String price, String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("cyberdemonclass@gmail.com");
        simpleMailMessage.setTo(toMail);
        simpleMailMessage.setText(bookName + "\n" + description + "\n" + price + "\n" + message);
        simpleMailMessage.setSubject("Your order");
        mailSender.send(simpleMailMessage);
    }
}
