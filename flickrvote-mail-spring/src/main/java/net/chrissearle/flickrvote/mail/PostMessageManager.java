package net.chrissearle.flickrvote.mail;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class PostMessageManager implements PostService {
    Logger logger = Logger.getLogger(PostMessageManager.class);

    private MailSender mailSender;
    private SimpleMailMessage mailTemplate;

    public PostMessageManager(MailSender mailSender, SimpleMailMessage mailTemplate) {
        this.mailSender = mailSender;
        this.mailTemplate = mailTemplate;
    }

    public void sendPost(String title, String text) {
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(mailTemplate);

        msg.setSubject(title);
        msg.setText(text);

        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            // We log this - it's not supposed to show up to the user - but should show in the system logs
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to send post mail", ex);
            }
        }
    }
}
