package net.chrissearle.flickrvote.web.model;

import java.util.Date;

public interface Challenge {
    String getTag();

    String getDescription();

    Date getStart();

    Date getVote();

    Date getEnd();

    boolean isOpen();

    boolean isClosed();

    boolean isVoting();
}
