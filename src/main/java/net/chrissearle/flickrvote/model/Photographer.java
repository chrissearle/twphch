package net.chrissearle.flickrvote.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Photographer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private String username;

    private String fullname;

    private Boolean administrator;

    public Photographer(Long id, String token, String username, String fullname) {
        this.setId(id);
        this.setToken(token);
        this.setUsername(username);
        this.setFullname(fullname);
        this.setAdministrator(false);
    }

    public Photographer(Photographer photographer) {
        this.setId(photographer.getId());
        this.setToken(photographer.getToken());
        this.setUsername(photographer.getUsername());
        this.setFullname(photographer.getFullname());
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

    @Override
    public String toString() {
        return new StringBuilder().append("ID: ").append(getId()).append(", USERNAME: ").append(getUsername())
                .append(", FULLNAME: ").append(getFullname()).append(", ADMINISTRATOR: ").append(isAdministrator())
                .toString();
    }
}