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
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.ImageDAO;
import net.chrissearle.flickrvote.flickr.model.FlickrImage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;

@Component
public class FlickrJImageDAO extends AbstractFlickrJImageSupport implements ImageDAO {
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

    private FlickrImage retrieveAndBuildImage(String id) throws IOException, FlickrException, SAXException {
        Photo photo = retrieveImage(id);

        if (logger.isInfoEnabled()) {
            logger.info("Image retrieved: " + id);
        }

        return buildImage(photo);
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
