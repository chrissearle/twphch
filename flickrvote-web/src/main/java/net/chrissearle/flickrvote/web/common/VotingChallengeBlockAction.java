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
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VotingChallengeBlockAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 1953138483852395428L;

    private transient Logger logger = Logger.getLogger(VotingChallengeBlockAction.class.getName());

    @Autowired
    private transient ChallengeService challengeService;

    private Challenge challenge;

    private Map<String, Object> session;

    private Boolean voted;

    public Challenge getChallenge() {
        return challenge;
    }

    @Override
    public String execute() throws Exception {
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        if (challenges.size() == 0) {
            return "empty";
        }

        // Front end assumes one voting challenge
        challenge = new DisplayChallengeSummary(challenges.iterator().next());

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Voting challenge: " + challenge);
        }

        if (session.containsKey(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY)) {
            Photographer photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

            voted = challengeService.hasVoted(photographer.getPhotographerId());

            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Setting voted to " + voted);
            }
        }

        return SUCCESS;
    }

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    public Boolean isVoted() {
        return voted;
    }
}
