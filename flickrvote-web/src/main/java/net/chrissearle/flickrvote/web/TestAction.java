package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.twitter.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.Status;

import java.util.List;

public class TestAction {
    @Autowired
    private TwitterService twitterService;

    private List<Status> twitterStatus;

    public String execute() throws Exception {
        setTwitterStatus(twitterService.getTimeline());

        return ActionSupport.SUCCESS;
    }

    public List<Status> getTwitterStatus() {
        return twitterStatus;
    }

    public void setTwitterStatus(List<Status> twitterStatus) {
        this.twitterStatus = twitterStatus;
    }
}
