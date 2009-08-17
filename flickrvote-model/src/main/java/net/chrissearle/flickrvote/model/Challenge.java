package net.chrissearle.flickrvote.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "challenge")
public class Challenge {

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

    @OneToMany(mappedBy = "challenge", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images = new ArrayList<Image>();

    @Version
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

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
        return votingOpenDate == null ? null : new Date(votingOpenDate.getTime());
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    protected void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate == null ? null : new Date(startDate.getTime());
    }

    public void setVotingOpenDate(Date votingOpenDate) {
        this.votingOpenDate = votingOpenDate == null ? null : new Date(votingOpenDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate == null ? null : new Date(endDate.getTime());
    }

    public Date getStartDate() {
        return new Date(startDate.getTime());
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

    public ChallengeState getVotingState() {
        long now = new Date().getTime();
        long end = getEndDate().getTime();
        long voting = getVotingOpenDate().getTime();

        if (now >= end) {
            return ChallengeState.CLOSED;
        }

        if (now >= voting && now < end) {
            return ChallengeState.VOTING;
        }

        return ChallengeState.OPEN;
    }
}
