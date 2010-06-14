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

package net.chrissearle.flickrvote.web.model;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;

import java.util.Date;

public class DisplayImage implements Image, Photographer, Challenge {
    private final String imageId;
    private final String imageTitle;
    private final String imagePageUrl;
    private final String imageUrl;
    private final String largeImageUrl;
    private final Date postedDate;
    private final Long voteCount;
    private final Long rank;
    private final String photographerId;
    private final String username;
    private final String fullname;
    private final String twitterAccount;
    private final String iconUrl;
    private final Boolean adminFlag;
    private final String challengeTag;
    private final String challengeDescription;
    private final Date challengeStart;
    private final Date challengeEnd;
    private final Date challengeVote;
    private final Boolean challengeOpen;
    private final Boolean challengeVoting;
    private final Boolean challengeClosed;
    private final Integer imageCount;
    private final String challengeNotes;

    public DisplayImage(ImageItem image) {
        this.imageId = image.getId();
        this.imageTitle = image.getTitle();
        this.imagePageUrl = image.getUrl();
        this.imageUrl = image.getImageUrl();
        this.largeImageUrl = image.getLargeImageUrl();
        this.postedDate = image.getPostedDate();
        this.voteCount = image.getVoteCount();
        this.rank = image.getRank();
        this.photographerId = image.getPhotographer().getId();
        this.username = image.getPhotographer().getUsername();
        this.fullname = image.getPhotographer().getFullname();
        this.twitterAccount = image.getPhotographer().getTwitter();
        this.iconUrl = image.getPhotographer().getIconUrl();
        this.adminFlag = image.getPhotographer().isAdministratorFlag();
        this.imageCount = image.getPhotographer().getImageCount();

        ChallengeSummary challengeSummary = image.getChallenge();

        if (challengeSummary != null) {
            this.challengeTag = challengeSummary.getTag();
            this.challengeDescription = challengeSummary.getTitle();
            this.challengeNotes = challengeSummary.getNotes();
            this.challengeStart = challengeSummary.getStartDate();
            this.challengeEnd = challengeSummary.getEndDate();
            this.challengeVote = challengeSummary.getVoteDate();
            this.challengeOpen = challengeSummary.isOpen();
            this.challengeVoting = challengeSummary.isVoting();
            this.challengeClosed = challengeSummary.isClosed();
        } else {
            // Means that it was originally a flickr search set
            this.challengeTag = "";
            this.challengeDescription = "";
            this.challengeNotes = "";
            this.challengeStart = null;
            this.challengeEnd = null;
            this.challengeVote = null;
            this.challengeOpen = true;
            this.challengeVoting = false;
            this.challengeClosed = false;
        }
    }

    public String getImageId() {
        return imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getPageUrl() {
        return imagePageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public Date getPostedDate() {
        return postedDate == null ? null : new Date(postedDate.getTime());
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public Long getRank() {
        return rank;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public String getPhotographerName() {
        if (fullname != null && !"".equals(fullname)) {
            return fullname;
        }
        return username;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Boolean isAdmin() {
        return adminFlag;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public String getChallengeTag() {
        return challengeTag;
    }

    public boolean isChallengeClosed() {
        return challengeClosed;
    }

    public String getChallengeDescription() {
        return challengeDescription;
    }

    public String getChallengeNotes() {
        return challengeNotes;
    }

    public Date getChallengeEnd() {
        return challengeEnd == null ? null : new Date(challengeEnd.getTime());
    }

    public boolean isChallengeOpen() {
        return challengeOpen;
    }

    public Date getChallengeStart() {
        return challengeStart == null ? null : new Date(challengeStart.getTime());
    }

    public Date getChallengeVote() {
        return challengeVote == null ? null : new Date(challengeVote.getTime());
    }

    public boolean isChallengeVoting() {
        return challengeVoting;
    }
}
