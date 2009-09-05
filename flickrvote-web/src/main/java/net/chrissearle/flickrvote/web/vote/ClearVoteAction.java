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

package net.chrissearle.flickrvote.web.vote;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Class ClearVoteAction clears the users votes then moves on to voting form
 *
 * @author chris
 *         Created on Sep 5, 2009
 */
public class ClearVoteAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1536112580959831367L;

    private Logger logger = Logger.getLogger(ClearVoteAction.class);

    private Map<String, Object> session;

    @Autowired
    private ChallengeService challengeService;

    @Override
    public String execute() throws Exception {
        Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        if (photographer != null) {
            if (logger.isInfoEnabled()) {
                logger.info("Clearing votes for " + photographer.getPhotographerName());
            }
        }

        challengeService.clearVotes(photographer.getPhotographerId());

        return SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }
}
