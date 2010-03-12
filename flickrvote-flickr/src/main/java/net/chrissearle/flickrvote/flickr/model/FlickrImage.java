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

package net.chrissearle.flickrvote.flickr.model;

import java.util.Date;

public class FlickrImage {
    private final String flickrId;
    private final String title;
    private final String url;
    private final String imageUrl;
    private final String largeImageUrl;
    private final Date postedDate;
    private FlickrPhotographer photographer;

    private Date takenDate;

    public FlickrImage(FlickrImage image, FlickrPhotographer photographer) {
        this.flickrId = image.getFlickrId();
        this.title = image.getTitle();
        this.url = image.getUrl();
        this.imageUrl = image.getImageUrl();
        this.largeImageUrl = image.getLargeImageUrl();
        this.takenDate = image.getTakenDate();
        this.postedDate = image.getPostedDate();
        this.photographer = photographer;

    }

    public FlickrImage(String flickrId, FlickrPhotographer photographer, String title, String url, String imageUrl, String largeImageUrl, Date takenDate, Date postedDate) {
        this.flickrId = flickrId;
        this.photographer = photographer;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.largeImageUrl = largeImageUrl;
        this.takenDate = new Date(takenDate.getTime());
        this.postedDate = new Date(postedDate.getTime());
    }

    public String getFlickrId() {
        return flickrId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public FlickrPhotographer getPhotographer() {
        return photographer;
    }

    public Date getTakenDate() {
        return new Date(takenDate.getTime());
    }

    public void setTakenDate(Date takenDate) {
        this.takenDate = new Date(takenDate.getTime());
    }

    public Date getPostedDate() {
        return new Date(postedDate.getTime());
    }

    public void setPhotographer(FlickrPhotographer photographer) {
        this.photographer = photographer;
    }

    @Override
    public String toString() {
        return "FlickrImage{" +
                "flickrId='" + flickrId + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                ", postedDate=" + postedDate +
                ", photographer=" + photographer +
                ", takenDate=" + takenDate +
                '}';
    }
}
