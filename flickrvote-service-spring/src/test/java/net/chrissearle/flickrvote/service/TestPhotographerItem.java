package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.PhotographerItem;

public class TestPhotographerItem implements PhotographerItem {
    Boolean activeFlag;
    Boolean administratorFlag;
    String fullname;
    String id;
    String twitter;
    String username;
    String iconUrl;

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Boolean isAdministratorFlag() {
        return administratorFlag;
    }

    public void setAdministratorFlag(Boolean administratorFlag) {
        this.administratorFlag = administratorFlag;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getName() {
        if (fullname != null && !"".equals(fullname)) {
            return fullname;
        }
        return username;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
