package net.chrissearle.flickrvote.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private String username;

    private String fullname;

    public User(Long id, String token, String username, String fullname) {
        this.setId(id);
        this.setToken(token);
        this.setUsername(username);
        this.setFullname(fullname);
    }

    public User(User user) {
        this.setId(user.getId());
        this.setToken(user.getToken());
        this.setUsername(user.getUsername());
        this.setFullname(user.getFullname());
    }

    protected User() {
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