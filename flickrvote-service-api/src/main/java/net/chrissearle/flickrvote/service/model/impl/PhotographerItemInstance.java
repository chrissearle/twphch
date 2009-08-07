package net.chrissearle.flickrvote.service.model.impl;

import net.chrissearle.flickrvote.flickr.FlickrPhotographer;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.PhotographerItem;

public class PhotographerItemInstance implements PhotographerItem {
    private final String id;
    private final String twitter;
    private final String fullname;
    private final String username;

    private final String iconUrl;

    private final Boolean administratorFlag;
    private final Boolean activeFlag;

    public PhotographerItemInstance(Photographer photographer) {
        this.id = photographer.getId();
        this.twitter = photographer.getTwitter();
        this.fullname = photographer.getFullname();
        this.username = photographer.getUsername();
        this.administratorFlag = photographer.isAdministrator();
        this.activeFlag = photographer.getToken() != null && photographer.getToken().length() == 0;
        this.iconUrl = photographer.getIconUrl();
    }

    public PhotographerItemInstance(FlickrPhotographer photographer) {
        this.id = photographer.getFlickrId();
        this.twitter = null;
        this.fullname = photographer.getRealname();
        this.username = photographer.getUsername();
        this.administratorFlag = false;
        this.activeFlag = photographer.getToken() != null && photographer.getToken().length() == 0;
        this.iconUrl = photographer.getIconUrl();
    }

    public Boolean isActiveFlag() {
        return activeFlag;
    }

    public Boolean isAdministratorFlag() {
        return administratorFlag;
    }

    public String getFullname() {
        return fullname;
    }

    public String getId() {
        return id;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getUsername() {
        return username;
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
}
