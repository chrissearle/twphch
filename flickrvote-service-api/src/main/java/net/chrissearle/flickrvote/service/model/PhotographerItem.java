package net.chrissearle.flickrvote.service.model;

/**
 * Created by IntelliJ IDEA.
 * User: chris
 * Date: Aug 7, 2009
 * Time: 12:53:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PhotographerItem {
    Boolean isActiveFlag();

    Boolean isAdministratorFlag();

    String getFullname();

    String getId();

    String getTwitter();

    String getUsername();

    String getIconUrl();

    String getName();
}
