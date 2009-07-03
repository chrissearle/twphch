package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Photographer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.NoResultException;

@Repository("photographerDao")
@Transactional
public class JpaPhotographerDao implements PhotographerDao {
    @PersistenceContext
    private EntityManager em;

    public Photographer findById(long id) {
        return em.find(Photographer.class, id);
    }

    public Photographer findByUsername(String username) {
        Query query = em.createQuery("select p from Photographer p where p.username = :username");
        query.setParameter("username", username);

        try {
            return (Photographer) query.getSingleResult();
        } catch (NoResultException e) {
            // Just means that there is no photographer yet present
            return null;
        }
    }

    public Photographer findByToken(String token) {
        Query query = em.createQuery("select p from Photographer p where p.token = :token");
        query.setParameter("token", token);

        try {
            return (Photographer) query.getSingleResult();
        } catch (NoResultException e) {
            // Just means that there is no photographer yet validated with flickr
            return null;
        }
    }

    public void save(Photographer photographer) {
        em.persist(photographer);
    }

    public Photographer update(Photographer photographer) {
        return em.merge(photographer);
    }

    public void delete(Photographer photographer) {
        em.remove(photographer);
    }
}
