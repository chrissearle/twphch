package net.chrissearle.flickrvote.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "challenge")
public class Challenge implements Comparable<Challenge> {

    @Id
    @Column(length = 50, name = "tag", nullable = false)
    private String tag;

    @Column(length = 255, name = "description", nullable = false)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "voting_open_date")
    private Date votingOpenDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(mappedBy = "challenge", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Image> images = new ArrayList<Image>();

    public Challenge(String tag, String name, Date startDate, Date votingOpenDate, Date endDate) {
        this.setTag(tag);
        this.setName(name);
        this.setStartDate(startDate);
        this.setVotingOpenDate(votingOpenDate);
        this.setEndDate(endDate);
    }

    protected Challenge() {
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
        return o.getStartDate().compareTo(getStartDate());
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
        return new StringBuilder().append(", TAG: ").append(getTag()).append(", NAME: ")
                .append(getName()).toString();
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        if (!images.contains(image)) {
            this.images.add(image);
            image.setChallenge(this);
        }
    }

    public void removeImage(Image image) {
        if (images.contains(image)) {
            this.images.remove(image);
            image.setChallenge(null);
        }
    }
}
