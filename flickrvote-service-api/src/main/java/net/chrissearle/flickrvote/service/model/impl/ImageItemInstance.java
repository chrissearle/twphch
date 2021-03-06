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

package net.chrissearle.flickrvote.service.model.impl;

import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.PhotographerItem;

import java.util.Date;

public class ImageItemInstance implements ImageItem {
    private static final long serialVersionUID = -5643560820163884573L;

    final private String id;
    final private String title;
    final private String url;
    final private String imageUrl;
    final private String largeImageUrl;
    final private Date postedDate;
    final private Long voteCount;
    final private Long rank;

    final private PhotographerItem photographer;
    final private ChallengeSummary challenge;

    public ImageItemInstance(FlickrImage image) {
        this.id = image.getFlickrId();
        this.title = image.getTitle();
        this.url = image.getUrl();
        this.imageUrl = image.getImageUrl();
        this.largeImageUrl = image.getLargeImageUrl();
        this.postedDate = image.getPostedDate();
        this.voteCount = 0L;
        this.rank = 0L;

        this.photographer = new PhotographerItemInstance(image.getPhotographer());
        this.challenge = null;
    }

    public ImageItemInstance(FlickrImage image, Photographer photographer) {
        this.id = image.getFlickrId();
        this.title = image.getTitle();
        this.url = image.getUrl();
        this.imageUrl = image.getImageUrl();
        this.largeImageUrl = image.getLargeImageUrl();
        this.postedDate = image.getPostedDate();
        this.voteCount = 0L;
        this.rank = 0L;

        this.photographer = new PhotographerItemInstance(photographer);
        this.challenge = null;
    }

    public ImageItemInstance(Image image) {
        this.id = image.getId();
        this.title = image.getTitle();
        this.url = image.getPage();
        this.imageUrl = image.getMediumImage();
        this.largeImageUrl = image.getLargeImage();
        this.postedDate = image.getPostedDate();
        switch (image.getChallenge().getVotingState()) {
            case CLOSED:
                this.voteCount = image.getFinalVoteCount();
                this.rank = image.getFinalRank();
                break;
            case VOTING:
                this.voteCount = (long) image.getVotes().size();
                this.rank = 0L;
                break;
            case OPEN:
            default:
                this.voteCount = 0L;
                this.rank = 0L;
        }

        this.photographer = new PhotographerItemInstance(image.getPhotographer());
        this.challenge = new ChallengeSummaryInstance(image.getChallenge());
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public PhotographerItem getPhotographer() {
        return photographer;
    }

    public Date getPostedDate() {
        return postedDate == null ? null : new Date(postedDate.getTime());
    }

    public Long getRank() {
        return rank;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public ChallengeSummary getChallenge() {
        return challenge;
    }

    @Override
    public String toString() {
        return "ImageItemInstance{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                ", postedDate=" + postedDate +
                ", voteCount=" + voteCount +
                ", rank=" + rank +
                '}';
    }
}
