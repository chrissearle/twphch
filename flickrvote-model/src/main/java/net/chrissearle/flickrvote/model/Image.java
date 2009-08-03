package net.chrissearle.flickrvote.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Entity
@Table(name = "image")
public class Image {

    @Column(length = 100, name = "title")
    private String title;

    @ManyToOne
    private Challenge challenge;

    @ManyToOne
    private Photographer photographer;

    @Id
    @Column(name = "flickr_id", length = 50)
    private String id;

    @Column(name = "page", length = 255)
    private String page;

    @Column(name = "medium_image", length = 255)
    private String mediumImage;

    @Column(name = "final_vote_count", nullable = false)
    private Long finalVoteCount = 0L;

    @Column(name = "final_rank", nullable = false)
    private Long finalRank = 0L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "posted_date")
    private Date postedDate;

    @OneToMany(mappedBy = "image", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private
    List<Vote> votes = new ArrayList<Vote>();

    @Version
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Image{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", page='" + page + '\'' +
                ", mediumImage='" + mediumImage + '\'' +
                ", finalVoteCount=" + finalVoteCount +
                ", finalRank=" + finalRank +
                ", postedDate=" + postedDate +
                ", version=" + version +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Image)) {
            return false;
        }

        Image img = (Image) obj;

        return getId().equals(img.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getFinalVoteCount() {
        return finalVoteCount;
    }

    public void setFinalVoteCount(Long finalVoteCount) {
        this.finalVoteCount = finalVoteCount;
    }

    public Long getFinalRank() {
        return finalRank;
    }

    public void setFinalRank(Long finalRank) {
        this.finalRank = finalRank;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setImage(this);
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }
}