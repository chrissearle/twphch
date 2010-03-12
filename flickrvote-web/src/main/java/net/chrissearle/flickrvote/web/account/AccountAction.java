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

package net.chrissearle.flickrvote.web.account;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.DisplayPhotographer;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class AccountAction extends ActionSupport implements SessionAware, Preparable {
    @Autowired
    PhotographyService photographyService;

    private Map<String, Object> session;

    private Photographer photographer;
    private String twitter;

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public void prepare() throws Exception {
        photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        if (photographer.getTwitterAccount() != null && (twitter == null || "".equals(twitter))) {
            twitter = photographer.getTwitterAccount();
        }
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    @Override
    public String execute() throws Exception {
        PhotographerItem updatedPhotographer = photographyService.setTwitter(photographer.getPhotographerId(), twitter);

        photographer = new DisplayPhotographer(updatedPhotographer);

        session.put(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY, photographer);

        return SUCCESS;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        if (twitter.startsWith("@")) {
            twitter = twitter.substring(1);
        }

        this.twitter = twitter;
    }
}
