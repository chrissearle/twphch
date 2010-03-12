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
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import net.chrissearle.flickrvote.flickr.FlickrLoginService;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import net.chrissearle.flickrvote.flickr.UserDAO;
import net.chrissearle.flickrvote.flickr.model.FlickrPhotographer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service("flickrLoginService")
public class FlickrJLoginService implements FlickrLoginService {
    private Logger logger = Logger.getLogger(this.getClass());

    private Flickr flickr;
    private UserDAO userDao;

    @Autowired
    public FlickrJLoginService(Flickr flickr, UserDAO userDao) {
        this.flickr = flickr;
        this.userDao = userDao;
    }

    protected FlickrJLoginService() {
        // For tests
    }

    public URL getLoginUrl() throws FlickrServiceException {
        return getLoginUrl(false);
    }

    public URL getLoginUrl(boolean write) throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            String frob = authInterface.getFrob();

            Permission permission = Permission.READ;

            if (write) {
                permission = Permission.WRITE;
            }

            return authInterface.buildAuthenticationUrl(permission, frob);
        } catch (MalformedURLException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }

    public FlickrPhotographer authenticate(String frob) throws FlickrServiceException {
        try {
            AuthInterface authInterface = flickr.getAuthInterface();

            Auth auth = authInterface.getToken(frob);

            // Auth user does not populate correct buddy icon
            FlickrPhotographer flickrPhotographer = userDao.getUser(auth.getUser().getId());

            if (logger.isInfoEnabled()) {
                logger.info(flickrPhotographer.getUsername() + " has just logged in");
            }

            return flickrPhotographer;
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        }
    }
}
