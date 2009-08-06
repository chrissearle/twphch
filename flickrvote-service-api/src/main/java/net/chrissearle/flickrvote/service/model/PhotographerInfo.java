package net.chrissearle.flickrvote.service.model;

import net.chrissearle.flickrvote.model.Photographer;

@Deprecated
public class PhotographerInfo {
    private String id;
    private String token;
    private String twitter;
    private Boolean administratorFlag;
    private String fullname;
    private String username;

    public PhotographerInfo(Photographer photographer) {
        this.id = photographer.getId();
        this.fullname = photographer.getFullname();
        this.username = photographer.getUsername();
        this.token = photographer.getToken();
        this.twitter = photographer.getTwitter();
        this.administratorFlag = photographer.isAdministrator();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        if (fullname != null && !"".equals(fullname)) {
            return fullname;
        }

        return username;
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

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }
}
