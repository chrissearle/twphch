package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.model.Photographer;

public interface PhotographerService {
    Photographer persistUser(Photographer photographer);

    Photographer getUser(String username);

    Photographer findByFlickrId(String id);
}