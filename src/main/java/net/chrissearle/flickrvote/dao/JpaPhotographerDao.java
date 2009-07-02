package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Photographer;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("photographerDao")
public class JpaPhotographerDao extends JpaDaoSupport implements PhotographerDao {
    public Photographer findById(long id) {
        return getJpaTemplate().find(Photographer.class, id);
    }

    public Photographer findByUsername(String username) {
        return (Photographer) getJpaTemplate().find("select u from net.chrissearle.flickrvote.model.Photographer u where u.username = ?1", username).iterator().next();
    }

    public Photographer findByToken(String token) {
        return (Photographer) getJpaTemplate().find("select u from net.chrissearle.flickrvote.model.Photographer u where u.token = ?1", token).iterator().next();
    }

    public void save(Photographer photographer) {
        getJpaTemplate().persist(photographer);
    }

    public Photographer update(Photographer photographer) {
        return getJpaTemplate().merge(photographer);
    }

    public void delete(Photographer photographer) {
        getJpaTemplate().remove(photographer);
    }
}
