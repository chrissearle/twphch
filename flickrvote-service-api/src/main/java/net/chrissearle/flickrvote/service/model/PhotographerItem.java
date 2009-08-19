package net.chrissearle.flickrvote.service.model;

import java.io.Serializable;

public interface PhotographerItem extends Serializable {
    Boolean isActiveFlag();

    Boolean isAdministratorFlag();

    String getFullname();

    String getId();

    String getTwitter();

    String getUsername();

    String getIconUrl();

    String getName();
}
