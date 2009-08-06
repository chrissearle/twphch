package net.chrissearle.flickrvote.web.model;

public interface Photographer {
    String getPhotographerId();

    String getPhotographerName();

    String getUsername();

    String getFullname();

    String getTwitterAccount();

    String getIconUrl();

    Boolean isAdmin();

    Boolean isActive();
}
