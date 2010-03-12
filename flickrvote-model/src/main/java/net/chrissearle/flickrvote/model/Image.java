/*
 * Copyright 2010 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class Image represents an entry for a given challenge.
 *
 * @author chris
 */
@Entity
@Table(name = "image")
public class Image {

    /**
     * The title of the image as set by the photographer. May be null.
     */
    @Column(length = 100, name = "title")
    private String title;

    /**
     * The challenge that this image is an entry for.
     */
    @ManyToOne
    private Challenge challenge;

    /**
     * The photographer who entered this image.
     */
    @ManyToOne
    private Photographer photographer;

    /**
     * The flickr ID of the image. Also used as primary key.
     */
    @Id
    @Column(name = "flickr_id", length = 50)
    private String id;

    /**
     * The URL (in string form) of the image page (image, comments, info etc) on flickr.
     */
    @Column(name = "page", length = 255)
    private String page;

    /**
     * The URL (in string form) of the medium format image on flickr.
     */
    @Column(name = "medium_image", length = 255)
    private String mediumImage;

    /**
     * The URL (in string form) of the large format image on flickr (if present, will be medium otherwise).
     */
    @Column(name = "large_image", length = 255)
    private String largeImage;

    /**
     * The final count of votes. Only meaningful after voting has ended.
     */
    @Column(name = "final_vote_count", nullable = false)
    private Long finalVoteCount = 0L;

    /**
     * The final rank of the image. Only meaningful after voting has ended.
     */
    @Column(name = "final_rank", nullable = false)
    private Long finalRank = 0L;

    /**
     * When the image was posted to flickr.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "posted_date")
    private Date postedDate;

    /**
     * The votes cast for this image. Only meaningful during voting and will be cleared at voting close.
     */
    @OneToMany(mappedBy = "image", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private
    List<Vote> votes = new ArrayList<Vote>();

    /**
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     */
    @Version
    private Long version;

    /**
     * Method getVersion returns the version of this Image object.
     * <p/>
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     *
     * @return the version (type Long) of this Image object.
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Method setVersion sets the version of this Image object.
     * <p/>
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     *
     * @param version the version of this Image object.
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return String
     */
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

    /**
     * Method getTitle returns the title of this Image object.
     * <p/>
     * The title of the image as set by the photographer. May be null.
     *
     * @return the title (type String) of this Image object.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method setTitle sets the title of this Image object.
     * <p/>
     * The title of the image as set by the photographer. May be null.
     *
     * @param title the title of this Image object.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method getChallenge returns the challenge of this Image object.
     * <p/>
     * The challenge that this image is an entry for.
     *
     * @return the challenge (type Challenge) of this Image object.
     */
    public Challenge getChallenge() {
        return challenge;
    }

    /**
     * Method setChallenge sets the challenge of this Image object.
     * <p/>
     * The challenge that this image is an entry for.
     *
     * @param challenge the challenge of this Image object.
     */
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    /**
     * Method getPhotographer returns the photographer of this Image object.
     * <p/>
     * The photographer who entered this image.
     *
     * @return the photographer (type Photographer) of this Image object.
     */
    public Photographer getPhotographer() {
        return photographer;
    }

    /**
     * Method setPhotographer sets the photographer of this Image object.
     * <p/>
     * The photographer who entered this image.
     *
     * @param photographer the photographer of this Image object.
     */
    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    /**
     * Method getPage returns the page of this Image object.
     * <p/>
     * The URL (in string form) of the image page (image, comments, info etc) on flickr.
     *
     * @return the page (type String) of this Image object.
     */
    public String getPage() {
        return page;
    }

    /**
     * Method setPage sets the page of this Image object.
     * <p/>
     * The URL (in string form) of the image page (image, comments, info etc) on flickr.
     *
     * @param page the page of this Image object.
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Method getMediumImage returns the mediumImage of this Image object.
     * <p/>
     * The URL (in string form) of the medium format image on flickr.
     *
     * @return the mediumImage (type String) of this Image object.
     */
    public String getMediumImage() {
        return mediumImage;
    }

    /**
     * Method setMediumImage sets the mediumImage of this Image object.
     * <p/>
     * The URL (in string form) of the medium format image on flickr.
     *
     * @param mediumImage the mediumImage of this Image object.
     */
    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    /**
     * Method getLargeImage returns the largeImage of this Image object.
     * <p/>
     * The URL (in string form) of the large (medium if no large) format image on flickr.
     *
     * @return the largeImage (type String) of this Image object.
     */
    public String getLargeImage() {
        return largeImage;
    }

    /**
     * Method setLargeImage sets the largeImage of this Image object.
     * <p/>
     * The URL (in string form) of the large (medium if no large) format image on flickr.
     *
     * @param mediumImage the largeImage of this Image object.
     */
    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    /**
     * Method equals checks for equality by checking the flickr ID
     *
     * @param obj of type Object
     * @return true if the flickr ID matches
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Image)) {
            return false;
        }

        Image img = (Image) obj;

        return getId().equals(img.getId());
    }

    /**
     * Method getId returns the id of this Image object.
     * <p/>
     * The flickr ID of the image. Also used as primary key.
     *
     * @return the id (type String) of this Image object.
     */
    public String getId() {
        return id;
    }

    /**
     * Method setId sets the id of this Image object.
     * <p/>
     * The flickr ID of the image. Also used as primary key.
     *
     * @param id the id of this Image object.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method getFinalVoteCount returns the finalVoteCount of this Image object.
     * <p/>
     * The final count of votes. Only meaningful after voting has ended.
     *
     * @return the finalVoteCount (type Long) of this Image object.
     */
    public Long getFinalVoteCount() {
        return finalVoteCount;
    }

    /**
     * Method setFinalVoteCount sets the finalVoteCount of this Image object.
     * <p/>
     * The final count of votes. Only meaningful after voting has ended.
     *
     * @param finalVoteCount the finalVoteCount of this Image object.
     */
    public void setFinalVoteCount(Long finalVoteCount) {
        this.finalVoteCount = finalVoteCount;
    }

    /**
     * Method getFinalRank returns the finalRank of this Image object.
     * <p/>
     * The final rank of the image. Only meaningful after voting has ended.
     *
     * @return the finalRank (type Long) of this Image object.
     */
    public Long getFinalRank() {
        return finalRank;
    }

    /**
     * Method setFinalRank sets the finalRank of this Image object.
     * <p/>
     * The final rank of the image. Only meaningful after voting has ended.
     *
     * @param finalRank the finalRank of this Image object.
     */
    public void setFinalRank(Long finalRank) {
        this.finalRank = finalRank;
    }

    /**
     * Method hashCode returns the hashcode using the same fields as equals (flickr ID)
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Method getVotes returns the votes of this Image object.
     * <p/>
     * The votes cast for this image. Only meaningful during voting and will be cleared at voting close.
     *
     * @return the votes (type List<Vote>) of this Image object.
     */
    public List<Vote> getVotes() {
        return votes;
    }

    /**
     * Method setVotes sets the votes of this Image object.
     * <p/>
     * The votes cast for this image. Only meaningful during voting and will be cleared at voting close.
     *
     * @param votes the votes of this Image object.
     */
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    /**
     * Method addVote adds a vote to this image and sets this image on the vote.
     *
     * @param vote of type Vote
     */
    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setImage(this);
    }

    /**
     * Method getPostedDate returns the postedDate of this Image object.
     * <p/>
     * When the image was posted to flickr.
     *
     * @return the postedDate (type Date) of this Image object.
     */
    public Date getPostedDate() {
        return postedDate == null ? null : new Date(postedDate.getTime());
    }

    /**
     * Method setPostedDate sets the postedDate of this Image object.
     * <p/>
     * When the image was posted to flickr.
     *
     * @param date the postedDate of this Image object.
     */
    public void setPostedDate(Date date) {
        postedDate = date == null ? null : new Date(date.getTime());
    }

}