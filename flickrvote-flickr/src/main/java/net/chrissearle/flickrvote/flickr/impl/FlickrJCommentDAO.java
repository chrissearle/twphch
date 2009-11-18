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
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.photos.comments.CommentsInterface;
import net.chrissearle.flickrvote.flickr.CommentDAO;
import net.chrissearle.flickrvote.flickr.FlickrServiceException;
import org.apache.log4j.Logger;
import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.IOException;

@Component
public class FlickrJCommentDAO implements CommentDAO {
    private Logger logger = Logger.getLogger(this.getClass());
    private boolean commentsActiveFlag;
    private String adminAuthToken;

    private Flickr flickr;

    @Autowired
    public FlickrJCommentDAO(Flickr flickr) {
        this.flickr = flickr;
    }

    @Configure
    public void configure(@Configuration(expression = "flickr.admin.auth.token") String adminToken,
                          @Configuration(expression = "flickr.admin.auth.active") Boolean adminActive) {
        this.commentsActiveFlag = adminActive;
        this.adminAuthToken = adminToken;
    }

    public void postComment(String imageId, String comment) {
        if (logger.isDebugEnabled()) {
            logger.debug("Posting comment check: " + commentsActiveFlag);
        }

        if (commentsActiveFlag) {
            if (logger.isInfoEnabled()) {
                logger.info("Image commented: " + imageId);

                if (logger.isDebugEnabled()) {
                    logger.debug("Commenting on : " + imageId + " with comment: " + comment);
                }
            }

            comment(imageId, comment);
        }

    }

    private void comment(String imageId, String comment) {
        try {
            setAuthInContext();

            postToFlickr(imageId, comment);
        } catch (IOException e) {
            throw new FlickrServiceException(e);
        } catch (SAXException e) {
            throw new FlickrServiceException(e);
        } catch (FlickrException e) {
            throw new FlickrServiceException(e);
        }
    }

    private void postToFlickr(String imageId, String comment) throws IOException, SAXException, FlickrException {
        CommentsInterface commentsInterface = flickr.getCommentsInterface();

        commentsInterface.addComment(imageId, comment);
    }

    private void setAuthInContext() throws IOException, SAXException, FlickrException {
        RequestContext context = RequestContext.getRequestContext();

        context.setAuth(getAdminAuth());
    }

    private Auth getAdminAuth() throws IOException, SAXException, FlickrException {
        AuthInterface authInterface = flickr.getAuthInterface();

        return authInterface.checkToken(adminAuthToken);
    }
}
