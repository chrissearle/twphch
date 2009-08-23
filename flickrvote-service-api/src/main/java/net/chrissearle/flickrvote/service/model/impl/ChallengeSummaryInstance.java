package net.chrissearle.flickrvote.service.model.impl;

import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.ChallengeState;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;

import java.util.Date;

public class ChallengeSummaryInstance implements ChallengeSummary {
    private static final long serialVersionUID = 4652749486204211082L;

    private String title;
    private String tag;
    private Date startDate;
    private Date endDate;
    private Date voteDate;

    boolean closed;
    boolean open;
    boolean voting;

    public ChallengeSummaryInstance(Challenge challenge) {
        title = challenge.getName();
        tag = challenge.getTag();
        startDate = challenge.getStartDate();
        endDate = challenge.getEndDate();
        voteDate = challenge.getVotingOpenDate();

        this.open = (challenge.getVotingState() == ChallengeState.OPEN);
        this.closed = (challenge.getVotingState() == ChallengeState.CLOSED);
        this.voting = (challenge.getVotingState() == ChallengeState.VOTING);
    }

    public String getTag() {
        return tag;
    }

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    public Date getVoteDate() {
        return voteDate == null ? null : new Date(voteDate.getTime());
    }

    public String getTitle() {
        return title;
    }

    public Boolean isClosed() {
        return closed;
    }

    public Boolean isVoting() {
        return voting;
    }

    public Boolean isOpen() {
        return open;
    }

    @Override
    public String toString() {
        return "ChallengeSummaryInstance{" +
                "closed=" + closed +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", voteDate=" + voteDate +
                ", open=" + open +
                ", voting=" + voting +
                '}';
    }
}