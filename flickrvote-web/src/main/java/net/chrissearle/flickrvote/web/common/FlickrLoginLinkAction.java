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

package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.flickr.FlickrLoginService;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class FlickrLoginLinkAction extends ActionSupport implements SessionAware {
    @Autowired
    private FlickrLoginService flickrLoginService;

    private String link;
    private Map<String, Object> session;

    @Override
    public String execute() {
        if (session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            return SUCCESS;
        }
        link = flickrLoginService.getLoginUrl().toExternalForm();

        return LOGIN;
    }

    public String getLink() {
        return link;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}
