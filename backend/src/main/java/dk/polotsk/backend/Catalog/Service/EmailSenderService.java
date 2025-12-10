package dk.polotsk.backend.Catalog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender MailSender;


    public void sendEmail(String toEmail,
                          String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kridtmandbob@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        MailSender.send(message);

        System.out.println("mail sendt til: " + toEmail + "succesfully");
    }

}
