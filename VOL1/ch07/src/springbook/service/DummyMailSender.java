package springbook.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Arrays;

public class DummyMailSender implements MailSender {
    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        System.out.println("from : " +simpleMailMessage.getFrom());
        System.out.println("to : " +Arrays.toString(simpleMailMessage.getTo()));
        System.out.println("subject : " +simpleMailMessage.getSubject());
        System.out.println("text : " +simpleMailMessage.getText());
    }

    @Override
    public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {
        System.out.println("실행2");
        System.out.println(simpleMailMessages);
    }
}
