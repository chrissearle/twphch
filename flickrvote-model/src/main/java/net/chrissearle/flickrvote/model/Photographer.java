package net.chrissearle.flickrvote.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Photographer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(length = 100, name = "token")
    private String token;

    @Column(length = 50, name = "username")
    private String username;

    @Column(length = 100, name = "fullname")
    private String fullname;

    @Column(name = "administrator")
    private Boolean administrator;

    @Column(name = "flickr_id", length = 50)
    private String flickrId;

    @OneToMany(mappedBy = "photographer")
    private List<Image> images = new ArrayList<Image>();

    public Photographer(Long id, String token, String username, String fullname, String flickrId) {
        this.setId(id);
        this.setToken(token);
        this.setUsername(username);
        this.setFullname(fullname);
        this.setFlickrId(flickrId);
        this.setAdministrator(false);
    }

    public Photographer(Photographer photographer) {
        this.setId(photographer.getId());
        this.setToken(photographer.getToken());
        this.setUsername(photographer.getUsername());
        this.setFullname(photographer.getFullname());
        this.setFlickrId(photographer.getFlickrId());
        this.setAdministrator(photographer.isAdministrator());
    }

    public Photographer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        }
    }

    public void removeImage(Image image) {
        if (images.contains(image)) {
            this.images.remove(image);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ID: ").append(getId()).append(", USERNAME: ").append(getUsername())
                .append(", FULLNAME: ").append(getFullname()).append(", ADMINISTRATOR: ").append(isAdministrator())
                .toString();
    }


    public String getFlickrId() {
        return flickrId;
    }

    public void setFlickrId(String flickrId) {
        this.flickrId = flickrId;
    }
}