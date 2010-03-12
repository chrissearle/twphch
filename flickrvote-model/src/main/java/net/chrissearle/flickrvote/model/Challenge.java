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
 * Class Challenge represents a single challenge
 *
 * @author chris
 */
@Entity
@Table(name = "challenge")
public class Challenge {

    /**
     * Tag is the primary key for a challenge. It is also the tag used at flickr
     * for tagging the challenge and for twitter keyword (however we do not store
     * the leading # in this object).
     */
    @Id
    @Column(length = 50, name = "tag", nullable = false)
    private String tag;

    /**
     * Name is the actual challenge description/theme/topic.
     */
    @Column(length = 255, name = "description", nullable = false)
    private String name;

    /**
     * Description
     */
    @Column(name = "notes")
    @Lob
    private String description;

    /**
     * Start date represents the date and time that a given challenge opens for entries.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    /**
     * Voting open date represents the date and time that a given challenge closes for entries
     * and opens for voting.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "voting_open_date")
    private Date votingOpenDate;

    /**
     * End date represents the date and time that a given challenge closes for voting and can be
     * regarded as complete.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    /**
     * Images is a mapped list of all images that are entered for this challenge
     */
    @OneToMany(mappedBy = "challenge", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images = new ArrayList<Image>();

    /**
     * The photographer who decided on this challenge.
     */
    @ManyToOne
    private Photographer photographer;


    /**
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     */
    @Version
    private Long version;

    /**
     * Method getVersion returns the version of this Challenge object.
     * <p/>
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     *
     * @return the version (type Long) of this Challenge object.
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Method setVersion sets the version of this Challenge object.
     * <p/>
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     *
     * @param version the version of this Challenge object.
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Constructor Challenge creates a new Challenge instance.
     *
     * @param tag            of type String
     * @param name           of type String
     * @param startDate      of type Date
     * @param votingOpenDate of type Date
     * @param endDate        of type Date
     */
    public Challenge(String tag, String name, Date startDate, Date votingOpenDate, Date endDate) {
        this.setTag(tag);
        this.setName(name);
        this.setStartDate(startDate);
        this.setVotingOpenDate(votingOpenDate);
        this.setEndDate(endDate);
    }

    /**
     * Constructor Challenge creates a new Challenge instance.
     */
    protected Challenge() {
    }

    /**
     * Method getTag returns the tag of this Challenge object.
     * <p/>
     * Tag is the primary key for a challenge. It is also the tag used at flickr
     *
     * @return the tag (type String) of this Challenge object.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Method getName returns the name of this Challenge object.
     * <p/>
     * Name is the actual challenge description/theme/topic.
     *
     * @return the name (type String) of this Challenge object.
     */
    public String getName() {
        return name;
    }

    /**
     * Method getVotingOpenDate returns the votingOpenDate of this Challenge object.
     * <p/>
     * Voting open date represents the date and time that a given challenge closes for entries
     *
     * @return the votingOpenDate (type Date) of this Challenge object.
     */
    public Date getVotingOpenDate() {
        return votingOpenDate == null ? null : new Date(votingOpenDate.getTime());
    }

    /**
     * Method getEndDate returns the endDate of this Challenge object.
     * <p/>
     * End date represents the date and time that a given challenge closes for voting and can be
     *
     * @return the endDate (type Date) of this Challenge object.
     */
    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    /**
     * Method setTag sets the tag of this Challenge object.
     * <p/>
     * Tag is the primary key for a challenge. It is also the tag used at flickr
     *
     * @param tag the tag of this Challenge object.
     */
    protected void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Method setName sets the name of this Challenge object.
     * <p/>
     * Name is the actual challenge description/theme/topic.
     *
     * @param name the name of this Challenge object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method setStartDate sets the startDate of this Challenge object.
     * <p/>
     * Start date represents the date and time that a given challenge opens for entries.
     *
     * @param startDate the startDate of this Challenge object.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate == null ? null : new Date(startDate.getTime());
    }

    /**
     * Method setVotingOpenDate sets the votingOpenDate of this Challenge object.
     * <p/>
     * Voting open date represents the date and time that a given challenge closes for entries
     *
     * @param votingOpenDate the votingOpenDate of this Challenge object.
     */
    public void setVotingOpenDate(Date votingOpenDate) {
        this.votingOpenDate = votingOpenDate == null ? null : new Date(votingOpenDate.getTime());
    }

    /**
     * Method setEndDate sets the endDate of this Challenge object.
     * <p/>
     * End date represents the date and time that a given challenge closes for voting and can be
     *
     * @param endDate the endDate of this Challenge object.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate == null ? null : new Date(endDate.getTime());
    }

    /**
     * Method getStartDate returns the startDate of this Challenge object.
     * <p/>
     * Start date represents the date and time that a given challenge opens for entries.
     *
     * @return the startDate (type Date) of this Challenge object.
     */
    public Date getStartDate() {
        return new Date(startDate.getTime());
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return new StringBuilder().append("TAG: ").append(getTag()).append(", NAME: ")
                .append(getName()).toString();
    }

    /**
     * Method getImages returns the images of this Challenge object.
     * <p/>
     * Images is a mapped list of all images that are entered for this challenge
     *
     * @return the images (type List<Image>) of this Challenge object.
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * Method addImage adds an image to this challenge and sets this challenge for the image.
     *
     * @param image of type Image
     */
    public void addImage(Image image) {
        if (!images.contains(image)) {
            this.images.add(image);
            image.setChallenge(this);
        }
    }

    /**
     * Method removeImage removes an image from this challenge and remvoes this challenge from the image.
     *
     * @param image of type Image
     */
    public void removeImage(Image image) {
        if (images.contains(image)) {
            this.images.remove(image);
            image.setChallenge(null);
        }
    }

    /**
     * Method getVotingState returns the votingState of this Challenge object.
     *
     * @return the votingState (type ChallengeState) of this Challenge object.
     */
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

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
