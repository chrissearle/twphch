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

package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.DisplayPhotographer;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginAsPhotographer extends ActionSupport implements SessionAware {
    private Logger logger = Logger.getLogger(LoginAsPhotographer.class.getName());

    private Map<String, Object> session;

    private String id;

    @Autowired
    private PhotographyService service;

    @Override
    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    @Override
    public String execute() throws Exception {
        Photographer admin = (Photographer)session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        Photographer photographer = new DisplayPhotographer(service.findById(id));

        session.put(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY, photographer);

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Admin " + admin.getPhotographerName() + " just logged in as " + photographer.getPhotographerName());
        }

        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
