/*
 * Copyright 2009 Chris Searle
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
import java.util.List;

@Entity
@Table(name = "photographer")
public class Photographer {

    @Column(length = 100, name = "token")
    private String token;

    @Column(length = 50, name = "username")
    private String username;

    @Column(length = 100, name = "fullname")
    private String fullname;

    @Column(length = 15, name = "twitter")
    private String twitter;

    @Column(name = "administrator")
    private Boolean administrator;

    @Id
    @Column(name = "flickr_id", length = 50)
    private String id;

    @OneToMany(mappedBy = "photographer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Image> images = new ArrayList<Image>();

    @OneToMany(mappedBy = "photographer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Vote> votes = new ArrayList<Vote>();

    @Version
    private Long version;

    @Column(name = "icon_url")
    private String iconUrl;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Photographer(String token, String username, String fullname, String id, String url) {
        this.setToken(token);
        this.setUsername(username);
        this.setFullname(fullname);
        this.setId(id);
        this.setAdministrator(false);
        this.setIconUrl(url);
    }

    public Photographer() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        if (!images.contains(image)) {
            this.images.add(image);
            image.setPhotographer(this);
        }
    }

    public void removeImage(Image image) {
        if (images.contains(image)) {
            this.images.remove(image);
            image.setPhotographer(null);
        }
    }

    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setPhotographer(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "Photographer{" +
                "administrator=" + administrator +
                ", token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", twitter='" + twitter + '\'' +
                ", id='" + id + '\'' +
                ", version=" + version +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}