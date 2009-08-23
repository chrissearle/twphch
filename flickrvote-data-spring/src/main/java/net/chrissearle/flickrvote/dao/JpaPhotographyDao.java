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
import net.chrissearle.flickrvote.model.Photographer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaPhotographyDao extends JpaDao<String, Photographer> implements PhotographyDao {
    private Logger log = Logger.getLogger(JpaPhotographyDao.class);

    public Photographer findByUsername(String username) {
        if (log.isDebugEnabled()) {
            log.debug("findByUsername : " + username);
        }

        Query query = entityManager.createQuery("select p from Photographer p where p.username = :username");
        query.setParameter("username", username);

        try {
            return (Photographer) query.getSingleResult();
        } catch (NoResultException e) {
            if (log.isDebugEnabled()) {
                log.debug("No matching user found");
            }

            // Just means that there is no photographer yet present
            return null;
        }
    }

    public Photographer findByToken(String token) {
        Query query = entityManager.createQuery("select p from Photographer p where p.token = :token");
        query.setParameter("token", token);

        try {
            return (Photographer) query.getSingleResult();
        } catch (NoResultException e) {
            // Just means that there is no photographer yet validated with flickr
            return null;
        }
    }

    @Override
    public void persist(Photographer photographer) {
        Photographer p = findByUsername(photographer.getUsername());

        if (p != null) {
            p.setFullname(photographer.getFullname());
            p.setToken(photographer.getToken());

            if (photographer.getId() != null) {
                p.setAdministrator(photographer.isAdministrator());
            }

            photographer = p;
        }

        super.persist(photographer);
    }

    public void clearVotes() {
        Query query = entityManager.createQuery("DELETE FROM Vote v");
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<Photographer> all() {
        Query query = entityManager.createQuery("SELECT p FROM Photographer p");

        return (List<Photographer>) query.getResultList();
    }
}
