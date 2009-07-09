package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Challenge;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Repository
public class JpaChallengeDao implements ChallengeDao {

    @PersistenceContext(unitName = "FlickrVote")
    private EntityManager em;

    public Challenge findById(long id) {
        return em.find(Challenge.class, id);
    }

    @SuppressWarnings("unchecked")
    public Challenge findByTag(String tag) {
        Query query = em.createQuery("select c from Challenge c where c.tag = :tag");
        query.setParameter("tag", tag);
        return (Challenge) query.getSingleResult();
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

    @SuppressWarnings("unchecked")
    public List<Challenge> getAll() {
        Query query = em.createQuery("select c from Challenge c");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Challenge> getClosedChallenges() {
        Query query = em.createQuery("select c from Challenge c where c.endDate < :now");
        query.setParameter("now", new Date());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public Challenge getCurrentChallenge() {
        Query query = em.createQuery("select c from Challenge c where c.startDate <= :now and c.votingOpenDate > :now");
        query.setParameter("now", new Date());

        List<Challenge> challenges = query.getResultList();

        if (challenges.size() > 0) {
            return challenges.iterator().next();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Challenge getVotingChallenge() {
        Query query = em.createQuery("select c from Challenge c where c.votingOpenDate <= :now and c.endDate > :now");
        query.setParameter("now", new Date());

        List<Challenge> challenges = query.getResultList();

        if (challenges.size() > 0) {
            return challenges.iterator().next();
        }

        return null;
    }
}