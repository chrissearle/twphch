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
        return new Date(start.getTime());
    }

    public Date getChallengeVote() {
        return new Date(vote.getTime());
    }

    public Date getChallengeEnd() {
        return new Date(end.getTime());
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
