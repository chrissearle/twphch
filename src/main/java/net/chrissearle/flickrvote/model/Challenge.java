package net.chrissearle.flickrvote.model;

import java.util.Date;

public class Challenge implements Comparable<Challenge> {
    private final Long id;

    private final String tag;

    private final String name;

    private final Date startDate;

    private final Date votingOpenDate;

    private final Date endDate;

    public Challenge(Long id, String tag, String name, Date startDate, Date votingOpenDate, Date endDate) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.startDate = startDate;
        this.votingOpenDate = votingOpenDate;
        this.endDate = endDate;
    }

    public Challenge(Challenge challenge) {
        this.id = challenge.getId();
        this.tag = challenge.getTag();
        this.name = challenge.getName();
        this.startDate = challenge.startDate;
        this.votingOpenDate = challenge.getVotingOpenDate();
        this.endDate = challenge.getEndDate();
    }


    public Long getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public Date getVotingOpenDate() {
        return votingOpenDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int compareTo(Challenge o) {
        return getTag().compareTo(o.getTag());
    }
}
