package net.chrissearle.twphch.flickrvote.server.model;

import javax.jdo.annotations.*;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Challenge {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    // Flickr username of the challenge creator
    @Persistent
    private String author;

    @Persistent
    private String challengeKey;

    @Persistent
    private String challengeName;

    @Persistent
    private Date startDate;

    @Persistent
    private Date stopDate;

    public Challenge(String author, String challengeKey, String challengeName, Date startDate, Date stopDate) {
        this.author = author;
        this.challengeKey = challengeKey;
        this.challengeName = challengeName;
        this.startDate = startDate;
        this.stopDate = stopDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getChallengeKey() {
        return challengeKey;
    }

    public void setChallengeKey(String challengeKey) {
        this.challengeKey = challengeKey;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public Long getId() {
        return id;
    }
}
