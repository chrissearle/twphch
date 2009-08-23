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

package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.web.model.DisplayPhotographer;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class FlickrCallbackAction implements SessionAware {
    private Logger logger = Logger.getLogger(FlickrCallbackAction.class);

    @Autowired
    private PhotographyService photographyService;

    private String frob;

    private Map<String, Object> session;

    public String getFrob() {
        return frob;
    }

    public void setFrob(String frob) {
        this.frob = frob;
    }

    public String execute() throws Exception {
        Photographer photographer = new DisplayPhotographer(photographyService.checkLoginAndStore(frob));

        session.put(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY, photographer);

        if (logger.isInfoEnabled()) {
            logger.info(photographer.getPhotographerName() + " just logged in");
        }

        return ActionSupport.SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}
