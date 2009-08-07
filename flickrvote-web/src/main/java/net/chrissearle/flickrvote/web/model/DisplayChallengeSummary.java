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

    public String getChallengeTag() {
        return tag;
    }

    public String getChallengeDescription() {
        return description;
    }

    public Date getChallengeStart() {
        return start;
    }

    public Date getChallengeVote() {
        return vote;
    }

    public Date getChallengeEnd() {
        return end;
    }

    public boolean isChallengeOpen() {
        return open;
    }

    public boolean isChallengeClosed() {
        return closed;
    }

    public boolean isChallengeVoting() {
        return voting;
    }
}
