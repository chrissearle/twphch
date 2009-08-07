package net.chrissearle.flickrvote.web.model;

import net.chrissearle.flickrvote.service.model.PhotographerItem;

public class DisplayPhotographer implements Photographer {
    private final String photographerId;
    private final String username;
    private final String fullname;
    private final String twitterAccount;
    private final String iconUrl;
    private final Boolean adminFlag;
    private final Boolean activeFlag;

    public DisplayPhotographer(PhotographerItem photographer) {
        this.photographerId = photographer.getId();
        this.username = photographer.getUsername();
        this.fullname = photographer.getFullname();
        this.twitterAccount = photographer.getTwitter();
        this.iconUrl = photographer.getIconUrl();
        this.adminFlag = photographer.isAdministratorFlag();
        this.activeFlag = photographer.isActiveFlag();
    }

    public Boolean isActive() {
        return activeFlag;
    }

    public Boolean isAdmin() {
        return adminFlag;
    }

    public String getFullname() {
        return fullname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public String getUsername() {
        return username;
    }

    public String getPhotographerName() {
        if (fullname != null && !"".equals(fullname)) {
            return fullname;
        }
        return username;
    }
}
