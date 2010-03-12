/*
 * Copyright 2010 Chris Searle
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

package net.chrissearle.flickrvote.flickr.impl;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Extras;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.ImageTagSearchDAO;
import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import net.chrissearle.flickrvote.flickr.model.FlickrImages;
import org.apache.log4j.Logger;
import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FlickrJImageTagSearchDAO extends AbstractFlickrJImageSupport implements ImageTagSearchDAO {
    private Logger logger = Logger.getLogger(this.getClass());

    private static final int MAX_SEARCH_HITS = 500;
    private static final int SEARCH_PAGE_ONE = 1;
    
    private String groupId;

    @Autowired
    public FlickrJImageTagSearchDAO(Flickr flickr) {
        this.flickr = flickr;
    }

    @Configure
    public void configure(@Configuration(expression = "flickr.twphch.group") String groupId) {
        this.groupId = groupId;
    }

    public FlickrImages searchTag(String tag, Date earliestDate) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for " + tag + " and date " + earliestDate);
        }

        if (earliestDate == null) {
            throw new FlickrServiceException("Earliest date cannot be null");
        }


        List<FlickrImage> imageList = new ArrayList<FlickrImage>();

        for (FlickrImage image : searchTag(tag).getImages()){
           if (image.getTakenDate() == null || image.getTakenDate().getTime() >= earliestDate.getTime()) {
               imageList.add(image);
           }
        }

        return new FlickrImages(imageList);
    }

    public FlickrImages searchTag(String tag) {
        if (logger.isDebugEnabled()) {
            logger.debug("Searching for " + tag);
        }

        try {
            List<FlickrImage> images = new ArrayList<FlickrImage>();

            for (Photo photo : retrieveByTag(tag)) {
                images.add(buildImage(photo));
            }

            return new FlickrImages(images);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Photo> retrieveByTag(String tag) throws IOException, SAXException, FlickrException {
        PhotosInterface photosInterface = flickr.getPhotosInterface();

        String[] tags = new String[SEARCH_PAGE_ONE];
        tags[0] = new StringBuilder().append("#").append(tag).toString();

        SearchParameters params = new SearchParameters();
        params.setTags(tags);

        params.setExtras(Extras.ALL_EXTRAS);

        params.setGroupId(groupId);

        List<Photo> photos = (List<Photo>) photosInterface.search(params, MAX_SEARCH_HITS, SEARCH_PAGE_ONE);

        return photos;
    }

    protected String getLargeUrl(Photo photo) {
        return photo.getLargeUrl();
    }
}