package net.chrissearle.flickrvote.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Challenge implements Comparable<Challenge> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(length = 50, name = "tag", nullable = false)
    private String tag;

    @Column(length = 255, name = "description", nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "voting_open_date")
    private Date votingOpenDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @OneToMany(mappedBy = "challenge")
    private List<Image> images = new ArrayList<Image>();

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
        return o.getStartDate().compareTo(getStartDate());
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

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        if (!images.contains(image)) {
            this.images.add(image);
        }
    }

    public void removeImage(Image image) {
        if (images.contains(image)) {
            this.images.remove(image);
        }
    }
}
