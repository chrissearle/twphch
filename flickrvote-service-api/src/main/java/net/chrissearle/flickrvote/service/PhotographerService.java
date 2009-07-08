package net.chrissearle.flickrvote.service;

public interface PhotographerService {
    void addPhotographer(String token, String username, String fullname, String flickrId);

    void setAdministrator(String username, Boolean adminFlag);

    Boolean isAdministrator(String username);
}