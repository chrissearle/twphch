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

package net.chrissearle.flickrvote.flickr.impl;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotosInterface;
import com.aetrion.flickr.photos.SearchParameters;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.ImageDAO;
import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import net.chrissearle.flickrvote.flickr.model.FlickrImages;
import net.chrissearle.flickrvote.flickr.model.FlickrPhotographer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FlickrJImageDAO implements ImageDAO {
    private Logger logger = Logger.getLogger(this.getClass());
    private Flickr flickr;

    private static final int MAX_SEARCH_HITS = 500;
    private static final int SEARCH_PAGE_ONE = 1;

    @Autowired
    public FlickrJImageDAO(Flickr flickr) {
        this.flickr = flickr;
    }

    public FlickrImage getImage(String id) {
        try {
            return retrieveAndBuildImage(id);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }

    }

    public FlickrImages searchTag(String tag, Date earliestDate) {
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

        // By grabbing the dates at this point - saves us a call to get image.
        // We don't bother asking for names here to save a call to get photographer since that is
        // still needed to get the user icon/avatar.
        params.setExtrasDateUpload(true);
        params.setExtrasDateTaken(true);

        List<Photo> photos = (List<Photo>) photosInterface.search(params, MAX_SEARCH_HITS, SEARCH_PAGE_ONE);

        return photos;
    }

    private FlickrImage retrieveAndBuildImage(String id) throws IOException, FlickrException, SAXException {
        Photo photo = retrieveImage(id);

        if (logger.isInfoEnabled()) {
            logger.info("Image retrieved: " + id);
        }

        return buildImage(photo);
    }

    private FlickrImage buildImage(Photo photo) {
        FlickrPhotographer photographer = new FlickrPhotographer(photo.getOwner().getId());

        return new FlickrImage(photo.getId(), photographer, photo.getTitle(), photo.getUrl(), photo.getMediumUrl(), photo.getDateTaken(), photo.getDatePosted());
    }

    private Photo retrieveImage(String id) throws IOException, FlickrException, SAXException {
        PhotosInterface photosInterface = flickr.getPhotosInterface();

        Photo photo = photosInterface.getPhoto(id);

        if (photo == null) {
            throw new FlickrServiceException("No photo found with ID: " + id);
        }

        return photo;
    }
}
