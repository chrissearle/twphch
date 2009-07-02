package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Photographer;

public interface PhotographerDao {
    public Photographer findById(long id);

    public Photographer findByUsername(String username);

    public Photographer findByToken(String token);

    public void save(Photographer photographer);

    public Photographer update(Photographer photographer);

    public void delete(Photographer photographer);
}