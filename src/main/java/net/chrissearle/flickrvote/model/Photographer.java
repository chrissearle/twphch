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

    public Photographer(Long id, String token, String username, String fullname) {
        this.setId(id);
        this.setToken(token);
        this.setUsername(username);
        this.setFullname(fullname);
    }

    public Photographer(Photographer photographer) {
        this.setId(photographer.getId());
        this.setToken(photographer.getToken());
        this.setUsername(photographer.getUsername());
        this.setFullname(photographer.getFullname());
    }

    protected Photographer() {
    }


    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    protected void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    protected void setFullname(String fullname) {
        this.fullname = fullname;
    }
}