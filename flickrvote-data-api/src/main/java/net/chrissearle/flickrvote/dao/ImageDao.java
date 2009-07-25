package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.Dao;
import net.chrissearle.flickrvote.model.Image;

import java.util.List;

public interface ImageDao extends Dao<String, Image> {
    List<Image> getImagesWithRank(long rank);
}