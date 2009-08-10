package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.mail.SimpleMailService;
import org.springframework.beans.factory.annotation.Autowired;

public class SendMailAction extends ActionSupport {
    private String title;
    private String text;

    @Autowired
    private SimpleMailService mailService;

    @Override
    public String execute() throws Exception {
        mailService.sendPost(title, text);

        addActionMessage("Mail sent");

        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
