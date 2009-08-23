/*
 * Copyright 2009 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.JpaDao;
import net.chrissearle.flickrvote.model.Challenge;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * Class JpaChallengeDao implements ChallengeDao using JPA
 *
 * @author chris
 */
@Repository
public class JpaChallengeDao extends JpaDao<String, Challenge> implements ChallengeDao {
    private Logger logger = Logger.getLogger(JpaChallengeDao.class);

    /**
     * Method findByTag returns the challenge with the given tag. Null if no matching challenge found.
     *
     * @param tag of type String
     * @return Challenge
     */
    @SuppressWarnings("unchecked")
    public Challenge findByTag(String tag) {
        Query query = entityManager.createQuery("select c from Challenge c where c.tag = :tag");
        query.setParameter("tag", tag);

        try {
            return (Challenge) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Method getAll returns all challenges.
     *
     * @return all challenges.
     */
    @SuppressWarnings("unchecked")
    public List<Challenge> getAll() {
        Query query = entityManager.createQuery("select c from Challenge c");
        return (List<Challenge>) query.getResultList();
    }

    /**
     * Method getClosedChallenges returns all challenges in a closed state.
     *
     * @return all closed challenges.
     */
    @SuppressWarnings("unchecked")
    public List<Challenge> getClosedChallenges() {
        Query query = entityManager.createQuery("select c from Challenge c where c.endDate < :now");
        query.setParameter("now", new Date());
        return (List<Challenge>) query.getResultList();
    }

    /**
     * Method getCurrentChallenge returns the the currently open challenge. Null if none found.
     *
     * @return the currently open challenge.
     */
    @SuppressWarnings("unchecked")
    public Challenge getCurrentChallenge() {
        Query query = entityManager.createQuery("select c from Challenge c where c.startDate <= :now and c.votingOpenDate > :now");
        query.setParameter("now", new Date());

        List<Challenge> challenges = (List<Challenge>) query.getResultList();

        if (challenges.size() > 0) {
            return challenges.iterator().next();
        }

        return null;
    }

    /**
     * Method getVotingChallenge returns the current voting challenge. Null if none found.
     *
     * @return the current voting challenge.
     */
    @SuppressWarnings("unchecked")
    public Challenge getVotingChallenge() {
        Query query = entityManager.createQuery("select c from Challenge c where c.votingOpenDate <= :now and c.endDate > :now");
        query.setParameter("now", new Date());

        List<Challenge> challenges = (List<Challenge>) query.getResultList();

        if (logger.isDebugEnabled()) {
            logger.debug("Voting challenges: " + challenges);
        }

        if (challenges.size() > 0) {
            return challenges.iterator().next();
        }

        return null;
    }

    /**
     * Method getVotedChallenge returns a challenge with existing votes. Returns null if none found.
     *
     * @return the challenge with votes.
     */
    @SuppressWarnings("unchecked")
    public Challenge getVotedChallenge() {
        Query query = entityManager.createQuery("select distinct c FROM Vote v, IN(v.image) i, IN(i.challenge) c");
        List<Challenge> challenges = (List<Challenge>) query.getResultList();

        if (challenges != null && challenges.size() > 0) {
            return challenges.iterator().next();
        }

        return null;
    }

    /**
     * Method findWithin finds the challenges where date is between start (inclusive) and voting open (exclusive).
     *
     * @param date of type Date
     * @return list of matching challenges
     */
    @SuppressWarnings("unchecked")
    public List<Challenge> findWithin(Date date) {
        Query query = entityManager.createQuery("select c from Challenge c where c.startDate <= :date and c.votingOpenDate > :date");
        query.setParameter("date", date);

        return (List<Challenge>) query.getResultList();
    }

    /**
     * Method getMostRecent returns the challenge with the latest start date.
     *
     * @return the challenge with the latest start date.
     */
    public Challenge getMostRecent() {
        Query query = entityManager.createQuery("select c from Challenge c where c.startDate = (select MAX(c.startDate) FROM c)");

        try {
            return (Challenge) query.getSingleResult();
        } catch (NoResultException e) {
            // Means nothing - just that the db is empty
            return null;
        }
    }
}
