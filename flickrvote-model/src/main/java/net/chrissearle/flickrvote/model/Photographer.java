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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    public Photographer(String token, String username, String fullname, String id) {
        this.setToken(token);
        this.setUsername(username);
        this.setFullname(fullname);
        this.setId(id);
        this.setAdministrator(false);
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


    @Override
    public String toString() {
        return new StringBuilder().append(", USERNAME: ").append(getUsername())
                .append(", FULLNAME: ").append(getFullname()).append(", ADMINISTRATOR: ").append(isAdministrator())
                .toString();
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
}