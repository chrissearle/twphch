package net.chrissearle.flickrvote.web.model;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;

import java.util.Date;

public class DisplayChallengeSummary implements Challenge {
    private String tag;
    private String description;
    private Date start;
    private Date vote;
    private Date end;
    private Boolean open;
    private Boolean closed;
    private Boolean voting;

    public DisplayChallengeSummary(ChallengeSummary summary) {
        tag = summary.getTag();
        description = summary.getTitle();
        start = summary.getStartDate();
        vote = summary.getVoteDate();
        end = summary.getEndDate();
        open = summary.isOpen();
        closed = summary.isClosed();
        voting = summary.isVoting();
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public Date getStart() {
        return start;
    }

    public Date getVote() {
        return vote;
    }

    public Date getEnd() {
        return end;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isVoting() {
        return voting;
    }
}
