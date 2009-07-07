package net.chrissearle.flickrvote.service;

import twitter4j.Status;

import java.util.List;

public interface TwitterService {
    void twitter(String text);

    List<Status> getTimeline();
}
