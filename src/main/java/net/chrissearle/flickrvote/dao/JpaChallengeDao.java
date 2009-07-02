package net.chrissearle.flickrvote.dao;

import net.chrissearle.flickrvote.model.Challenge;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.orm.jpa.support.JpaDaoSupport;

@Repository("challengeDao")
public class JpaChallengeDao extends JpaDaoSupport implements ChallengeDao {
    public Challenge findById(long id) {
        return getJpaTemplate().find(Challenge.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Challenge> findByTag(String tag) {
        return getJpaTemplate().find("select c from Challenge c where c.tag = ?1", tag);
    }

    public void save(Challenge challenge) {
        getJpaTemplate().persist(challenge);
    }

    public Challenge update(Challenge challenge) {
        return getJpaTemplate().merge(challenge);
    }

    public void delete(Challenge challenge) {
        getJpaTemplate().remove(challenge);
    }
}
