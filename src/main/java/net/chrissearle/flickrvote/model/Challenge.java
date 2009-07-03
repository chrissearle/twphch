package net.chrissearle.flickrvote.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Challenge implements Comparable<Challenge> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tag;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date votingOpenDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    public Challenge(Long id, String tag, String name, Date startDate, Date votingOpenDate, Date endDate) {
        this.setId(id);
        this.setTag(tag);
        this.setName(name);
        this.setStartDate(startDate);
        this.setVotingOpenDate(votingOpenDate);
        this.setEndDate(endDate);
    }

    public Challenge(Challenge challenge) {
        this.setId(challenge.getId());
        this.setTag(challenge.getTag());
        this.setName(challenge.getName());
        this.setStartDate(challenge.getStartDate());
        this.setVotingOpenDate(challenge.getVotingOpenDate());
        this.setEndDate(challenge.getEndDate());
    }

    protected Challenge() {
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

    protected void setId(Long id) {
        this.id = id;
    }

    protected void setTag(String tag) {
        this.tag = tag;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    protected void setVotingOpenDate(Date votingOpenDate) {
        this.votingOpenDate = votingOpenDate;
    }

    protected void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ID: ").append(getId()).append(", TAG: ").append(getTag()).append(", NAME: ")
                .append(getName()).toString();
    }
}
