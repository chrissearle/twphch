package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.JpaDao;
import net.chrissearle.flickrvote.model.Image;
import org.springframework.stereotype.Repository;

@Repository
public class JpaImageDao extends JpaDao<String, Image> implements ImageDao {
}