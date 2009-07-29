package net.chrissearle.flickrvote.service.model;

import net.chrissearle.flickrvote.model.Photographer;

public class PhotographerInfo {
    private String id;
    private String name;
    private String token;
    private String twitter;
    private Boolean administratorFlag;

    public PhotographerInfo(Photographer photographer) {
        this.id = photographer.getId();
        this.name = photographer.getFullname() != null ? photographer.getFullname() : photographer.getUsername();
        this.token = photographer.getToken();
        this.twitter = photographer.getTwitter();
        this.administratorFlag = photographer.isAdministrator();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public Boolean isAdministrator() {
        return administratorFlag;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
