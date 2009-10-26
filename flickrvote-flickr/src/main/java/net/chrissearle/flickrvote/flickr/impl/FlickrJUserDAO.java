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
import com.aetrion.flickr.people.PeopleInterface;
import com.aetrion.flickr.people.User;
import net.chrissearle.flickrvote.flickr.FlickrPhotographer;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;

@Component
public class FlickrJUserDAO implements UserDAO {
    private Logger logger = Logger.getLogger(this.getClass());
    private Flickr flickr;

    @Autowired
    public FlickrJUserDAO(Flickr flickr) {
        this.flickr = flickr;
    }

    public FlickrPhotographer getUser(String id) {
        try {
            return retrieveAndBuildPhotographer(id);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }

    private FlickrPhotographer retrieveAndBuildPhotographer(String id) throws IOException, SAXException, FlickrException {
        User user = retrieveUser(id);

        if (logger.isInfoEnabled()) {
            logger.info("User info fetched for " + user.getUsername());
        }

        return buildPhotographer(user);
    }

    private FlickrPhotographer buildPhotographer(User user) {
        return new FlickrPhotographer(user.getId(), user.getUsername(), user.getRealName(), user.getBuddyIconUrl());
    }

    private User retrieveUser(String id) throws IOException, SAXException, FlickrException {
        PeopleInterface peopleInterface = flickr.getPeopleInterface();

        User user = peopleInterface.getInfo(id);

        if (user == null) {
            throw new FlickrServiceException("No user found with ID: " + id);
        }

        return user;
    }
}
