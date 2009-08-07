package net.chrissearle.flickrvote.service.model;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: chris
 * Date: Aug 7, 2009
 * Time: 12:55:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ChallengeSummary {
    String getTag();

    Date getStartDate();

    Date getEndDate();

    Date getVoteDate();

    String getTitle();

    Boolean isClosed();

    Boolean isVoting();

    Boolean isOpen();
}
