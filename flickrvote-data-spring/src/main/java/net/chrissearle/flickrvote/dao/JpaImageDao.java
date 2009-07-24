package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.JpaDao;
import net.chrissearle.flickrvote.model.Image;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository

public class JpaImageDao extends JpaDao<String, Image> implements ImageDao {
    private Logger log = Logger.getLogger(JpaImageDao.class);

    public Image findImageByFlickrId(String id) {
        Query query = entityManager.createQuery("select i from Image i where i.id  = :id");
        query.setParameter("id", id);

        try {
            return (Image) query.getSingleResult();
        } catch (NoResultException e) {
            // Just means that there is no photographer yet validated with flickr
            return null;
        }
    }
}