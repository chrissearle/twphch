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
import com.opensymphony.xwork2.Validateable;
import net.chrissearle.flickrvote.flickr.FlickrStatusCheckService;
import net.chrissearle.flickrvote.flickr.model.FlickrImageStatus;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.ServiceException;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class GetPhotoAction extends ActionSupport implements Preparable, SessionAware, Validateable {
    @Autowired
    PhotographyService photographyService;

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private FlickrStatusCheckService flickrStatusCheckService;

    private Map<String, Object> session;

    private String id;

    private Photographer photographer;
    private String challengeTag;
    private Date challengeStart;


    private ImageItem image;

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    @Override
    public void validate() {
        FlickrImageStatus status = flickrStatusCheckService.checkSearch(challengeTag, challengeStart, id);

        if (status == null) {
            try {
                image = photographyService.retrieveAndStoreImageForPhotographer(id, challengeTag, photographer.getPhotographerId());

                addActionMessage(image.getTitle() + " OK");
            } catch (ServiceException se) {
                addActionError(getText(se.getMessage()));
            }
        } else {
            addActionError(getText("date.error"));
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void prepare() throws Exception {
        photographer = (Photographer) session.get(FlickrVoteWebConstants.FLICKR_USER_SESSION_KEY);

        // Get a list of all open challenges
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.OPEN);

        // Currently the web layer is designed for one current challengeSummary only
        if (challenges.size() > 0) {
            ChallengeSummary challengeSummary = challenges.iterator().next();
            challengeTag = challengeSummary.getTag();
            challengeStart = challengeSummary.getStartDate();
        }
    }
}
