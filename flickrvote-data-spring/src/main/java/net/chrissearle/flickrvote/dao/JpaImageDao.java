package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.JpaDao;
import net.chrissearle.flickrvote.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaImageDao extends JpaDao<String, Image> implements ImageDao {
    @SuppressWarnings("unchecked")
    public List<Image> getImagesWithRank(long rank) {
        Query query = entityManager.createQuery("select i from Image i where i.finalRank = :rank");
        query.setParameter("rank", rank);

        return (List<Image>) query.getResultList();
    }
}