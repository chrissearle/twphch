package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.Dao;
import net.chrissearle.flickrvote.model.Photographer;

public interface PhotographyDao extends Dao<String, Photographer> {
    Photographer findByUsername(String username);

    Photographer findByToken(String token);

    void clearVotes();
}