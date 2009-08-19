package net.chrissearle.flickrvote.service.model;

import java.util.Date;
import java.io.Serializable;

public interface ChallengeSummary extends Serializable {
    String getTag();

    Date getStartDate();

    Date getEndDate();

    Date getVoteDate();

    String getTitle();

    Boolean isClosed();

    Boolean isVoting();

    Boolean isOpen();
}
