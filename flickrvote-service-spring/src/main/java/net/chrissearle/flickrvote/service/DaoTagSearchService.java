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

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.PhotographyDao;
import net.chrissearle.flickrvote.flickr.ImageTagSearchDAO;
import net.chrissearle.flickrvote.flickr.UserDAO;
import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import net.chrissearle.flickrvote.flickr.model.FlickrImages;
import net.chrissearle.flickrvote.flickr.model.FlickrPhotographer;
import net.chrissearle.flickrvote.model.Photographer;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageItems;
import net.chrissearle.flickrvote.service.model.impl.ImageItemInstance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DaoTagSearchService implements TagSearchService {
    private Logger logger = Logger.getLogger(this.getClass());

    private ImageTagSearchDAO flickrImageTagSearchDao;
    private UserDAO flickrUserDao;
    private PhotographyDao photographyDao;

    @Autowired
    public DaoTagSearchService(ImageTagSearchDAO flickrImageTagSearchDao, UserDAO flickrUserDao,
                               PhotographyDao photographyDao) {
        this.flickrImageTagSearchDao = flickrImageTagSearchDao;
        this.flickrUserDao = flickrUserDao;
        this.photographyDao = photographyDao;
    }


    public ImageItems searchByTagAndDate(String tag, Date earliestDate) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for Tag: " + tag + " with date " + earliestDate);
        }

        FlickrImages images = flickrImageTagSearchDao.searchTag(tag, earliestDate);

        List<ImageItem> items = new ArrayList<ImageItem>();

        for (FlickrImage flickrImage : images.getImages()) {
            ImageItem item = new ImageItemInstance(new FlickrImage(flickrImage, retrievePhotographer(flickrImage.getPhotographer().getFlickrId())));

            items.add(item);
        }

        return new ImageItems(items);
    }

    private FlickrPhotographer retrievePhotographer(String flickrId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for photographer: " + flickrId);
        }

        FlickrPhotographer flickrPhotographer;

        Photographer photographer = photographyDao.findById(flickrId);

        if (photographer != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Found: " + photographer);
            }

            flickrPhotographer = new FlickrPhotographer(photographer.getId(),
                    photographer.getToken(),
                    photographer.getUsername(),
                    photographer.getFullname(),
                    photographer.getIconUrl());
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Retrieving: " + flickrId);
            }

            flickrPhotographer = flickrUserDao.getUser(flickrId);

            photographyDao.persist(new Photographer(flickrPhotographer.getToken(), flickrPhotographer.getUsername(),
                    flickrPhotographer.getRealname(), flickrPhotographer.getFlickrId(), flickrPhotographer.getIconUrl()));
        }

        return flickrPhotographer;
    }
}
