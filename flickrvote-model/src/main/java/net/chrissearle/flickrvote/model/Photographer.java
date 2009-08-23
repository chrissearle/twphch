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

/**
 * Class Photographer represents a flickr user.
 *
 * @author chris
 */
@Entity
@Table(name = "photographer")
public class Photographer {

    /**
     * Authentication token for flickr. Obtained from flickr during login.
     */
    @Column(length = 100, name = "token")
    private String token;

    /**
     * Flickr username
     */
    @Column(length = 50, name = "username")
    private String username;

    /**
     * Flickr fullname. May be null.
     */
    @Column(length = 100, name = "fullname")
    private String fullname;

    /**
     * Twitter username. May be null.
     */
    @Column(length = 15, name = "twitter")
    private String twitter;

    /**
     * Administrator flag
     */
    @Column(name = "administrator")
    private Boolean administrator;

    /**
     * Flickr ID
     */
    @Id
    @Column(name = "flickr_id", length = 50)
    private String id;

    /**
     * List of all images that this photographer has entered for any challenge.
     */
    @OneToMany(mappedBy = "photographer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Image> images = new ArrayList<Image>();

    /**
     * List of all votes that this photographer has cast during the current round. Cleared at end
     * of voting.
     */
    @OneToMany(mappedBy = "photographer", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Vote> votes = new ArrayList<Vote>();

    /**
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     */
    @Version
    private Long version;

    /**
     * URL to users avatar on flickr. May be null.
     */
    @Column(name = "icon_url")
    private String iconUrl;

    /**
     * Method getVersion returns the version of this Photographer object.
     * <p/>
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     *
     * @return the version (type Long) of this Photographer object.
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Method setVersion sets the version of this Photographer object.
     * <p/>
     * Version is an internal field used by JPA for preventing issues with concurrent updates.
     *
     * @param version the version of this Photographer object.
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Constructor Photographer creates a new Photographer instance.
     *
     * @param token    of type String
     * @param username of type String
     * @param fullname of type String
     * @param id       of type String
     * @param url      of type String
     */
    public Photographer(String token, String username, String fullname, String id, String url) {
        this.setToken(token);
        this.setUsername(username);
        this.setFullname(fullname);
        this.setId(id);
        this.setAdministrator(false);
        this.setIconUrl(url);
    }

    /**
     * Constructor Photographer creates a new Photographer instance.
     */
    public Photographer() {
    }

    /**
     * Method getToken returns the token of this Photographer object.
     * <p/>
     * Authentication token for flickr. Obtained from flickr during login.
     *
     * @return the token (type String) of this Photographer object.
     */
    public String getToken() {
        return token;
    }

    /**
     * Method setToken sets the token of this Photographer object.
     * <p/>
     * Authentication token for flickr. Obtained from flickr during login.
     *
     * @param token the token of this Photographer object.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Method getUsername returns the username of this Photographer object.
     * <p/>
     * Flickr username
     *
     * @return the username (type String) of this Photographer object.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method setUsername sets the username of this Photographer object.
     * <p/>
     * Flickr username
     *
     * @param username the username of this Photographer object.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method getFullname returns the fullname of this Photographer object.
     * <p/>
     * Flickr fullname. May be null.
     *
     * @return the fullname (type String) of this Photographer object.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Method setFullname sets the fullname of this Photographer object.
     * <p/>
     * Flickr fullname. May be null.
     *
     * @param fullname the fullname of this Photographer object.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Method isAdministrator returns the administrator of this Photographer object.
     * <p/>
     * Administrator flag
     *
     * @return the administrator (type Boolean) of this Photographer object.
     */
    public Boolean isAdministrator() {
        return administrator;
    }

    /**
     * Method setAdministrator sets the administrator of this Photographer object.
     * <p/>
     * Administrator flag
     *
     * @param administrator the administrator of this Photographer object.
     */
    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    /**
     * Method getImages returns the images of this Photographer object.
     * <p/>
     * List of all images that this photographer has entered for any challenge.
     *
     * @return the images (type List<Image>) of this Photographer object.
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * Method addImage adds an image to this photographer and sets this photographer for the image.
     *
     * @param image of type Image
     */
    public void addImage(Image image) {
        if (!images.contains(image)) {
            this.images.add(image);
            image.setPhotographer(this);
        }
    }

    /**
     * Method removeImage removes an image from this photographer and removes this photographer from the image.
     *
     * @param image of type Image
     */
    public void removeImage(Image image) {
        if (images.contains(image)) {
            this.images.remove(image);
            image.setPhotographer(null);
        }
    }

    /**
     * Method addVote adds a vote to this photographer and sets this photographer on the vote.
     *
     * @param vote of type Vote
     */
    public void addVote(Vote vote) {
        votes.add(vote);
        vote.setPhotographer(this);
    }

    /**
     * Method getId returns the id of this Photographer object.
     * <p/>
     * Flickr ID
     *
     * @return the id (type String) of this Photographer object.
     */
    public String getId() {
        return id;
    }

    /**
     * Method setId sets the id of this Photographer object.
     * <p/>
     * Flickr ID
     *
     * @param id the id of this Photographer object.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method getVotes returns the votes of this Photographer object.
     * <p/>
     * List of all votes that this photographer has cast during the current round. Cleared at end
     *
     * @return the votes (type List<Vote>) of this Photographer object.
     */
    public List<Vote> getVotes() {
        return votes;
    }

    /**
     * Method setVotes sets the votes of this Photographer object.
     * <p/>
     * List of all votes that this photographer has cast during the current round. Cleared at end
     *
     * @param votes the votes of this Photographer object.
     */
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    /**
     * Method getTwitter returns the twitter of this Photographer object.
     * <p/>
     * Twitter username. May be null.
     *
     * @return the twitter (type String) of this Photographer object.
     */
    public String getTwitter() {
        return twitter;
    }

    /**
     * Method setTwitter sets the twitter of this Photographer object.
     * <p/>
     * Twitter username. May be null.
     *
     * @param twitter the twitter of this Photographer object.
     */
    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    /**
     * Method getIconUrl returns the iconUrl of this Photographer object.
     * <p/>
     * URL to users avatar on flickr. May be null.
     *
     * @return the iconUrl (type String) of this Photographer object.
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Method setIconUrl sets the iconUrl of this Photographer object.
     * <p/>
     * URL to users avatar on flickr. May be null.
     *
     * @param iconUrl the iconUrl of this Photographer object.
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Method toString
     *
     * @return String
     */
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