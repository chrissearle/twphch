package net.chrissearle.flickrvote.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

@Service
public class ForumPostMessageManager implements ForumPostService {
    Logger logger = Logger.getLogger(ForumPostMessageManager.class);
    
    private MailSender mailSender;
    private SimpleMailMessage mailTemplate;

    public ForumPostMessageManager(MailSender mailSender, SimpleMailMessage mailTemplate) {
        this.mailSender = mailSender;
        this.mailTemplate = mailTemplate;
    }

    public void sendForumPost(String title, String text) {
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(mailTemplate);

        msg.setSubject(title);
        msg.setText(text);

        try{
            this.mailSender.send(msg);
        } catch(MailException ex) {
            // We log this - it's not supposed to show up to the user - but should show in the system logs
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to send forum post mail", ex);
            }
        }
    }
}
