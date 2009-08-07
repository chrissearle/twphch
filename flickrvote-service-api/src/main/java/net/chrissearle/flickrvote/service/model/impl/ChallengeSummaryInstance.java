package net.chrissearle.flickrvote.service.model.impl;

import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.model.ChallengeState;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;

import java.util.Date;

public class ChallengeSummaryInstance implements ChallengeSummary {
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
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ChallengeInfo{")
                .append("title='").append(title)
                .append(", tag='").append(tag)
                .append(", startDate=").append(startDate)
                .append(", endDate=").append(endDate)
                .append(", voteDate=").append(voteDate)
                .append("}")
                .toString();
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
}