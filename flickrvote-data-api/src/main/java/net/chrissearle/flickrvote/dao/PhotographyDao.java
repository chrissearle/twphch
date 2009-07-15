package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.model.Photographer;

public interface PhotographyDao {
    Photographer findById(long id);

    Photographer findByUsername(String username);

    Photographer findByToken(String token);

    void save(Photographer photographer);

    void delete(Photographer photographer);

    Photographer findPhotographerByFlickrId(String id);

    Image findImageByFlickrId(String id);

    void save(Image image);
}