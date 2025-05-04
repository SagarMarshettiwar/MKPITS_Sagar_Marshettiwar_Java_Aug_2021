package com.example.MKApi.Services;

import java.util.Properties;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    public boolean sendEmail(String to, String subject, String message) {
        boolean isSent = false;
        String from = "betadynamodeveloper@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smpt.port", "587");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Create session object with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("betadynamodeveloper@gmail.com", "seckgxbyaijyyauf");
            }
        });

        session.setDebug(true);

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(message, "text/html");

            // Send email
            Transport.send(mimeMessage);
            System.out.println("Email sent successfully.");
            isSent = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return isSent;
    }
}
