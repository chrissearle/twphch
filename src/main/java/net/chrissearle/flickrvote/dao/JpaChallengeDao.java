package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Challenge;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("challengeDao")
@Transactional
public class JpaChallengeDao implements ChallengeDao {
    @PersistenceContext
    private EntityManager em;

    public Challenge findById(long id) {
        return em.find(Challenge.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Challenge> findByTag(String tag) {
        Query query = em.createQuery("select c from Challenge c where c.tag = :tag");
        query.setParameter("tag", tag);
        return query.getResultList();
    }

    public void save(Challenge challenge) {
        em.persist(challenge);
    }

    public Challenge update(Challenge challenge) {
        return em.merge(challenge);
    }

    public void delete(Challenge challenge) {
        em.remove(challenge);
    }
}
