package net.chrissearle.flickrvote.service.model;

import net.chrissearle.flickrvote.model.Challenge;

import java.util.Date;

public class ChallengeInfo implements Comparable<ChallengeInfo> {
    private String title;
    private String tag;
    private Date startDate;
    private Date endDate;
    private Date voteDate;

    public ChallengeInfo() {

    }

    public ChallengeInfo(String title, String tag, Date startDate, Date endDate, Date voteDate) {
        this.setTitle(title);
        this.setTag(tag);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setVoteDate(voteDate);
    }

    public ChallengeInfo(Challenge challenge) {
        this.setTitle(challenge.getName());
        this.setTag(challenge.getTag());
        this.setStartDate(challenge.getStartDate());
        this.setEndDate(challenge.getEndDate());
        this.setVoteDate(challenge.getVotingOpenDate());
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
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

    public int compareTo(ChallengeInfo challengeInfo) {
        return challengeInfo.getStartDate().compareTo(getStartDate());
    }

    public Boolean isClosed() {
        Date now = new Date();

        return now.getTime() > endDate.getTime();
    }

    public Boolean isVoting() {
        Date now = new Date();

        return now.getTime() < endDate.getTime() && now.getTime() > voteDate.getTime();
    }
}
