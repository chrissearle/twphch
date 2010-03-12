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

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.flickr.FlickrStatusCheckService;
import net.chrissearle.flickrvote.flickr.model.FlickrImageStatus;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;
import net.chrissearle.flickrvote.service.model.impl.ChallengeSummaryInstance;
import net.chrissearle.flickrvote.service.model.impl.ImageItemStatusInstance;
import net.chrissearle.mail.SimpleMailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("statusCheckService")
@Transactional
public class DaoStatusCheckService implements StatusCheckService {
    private Logger logger = Logger.getLogger(DaoStatusCheckService.class);

    private ChallengeDao challengeDao;
    private FlickrStatusCheckService flickrStatusCheckService;
    private SimpleMailService mailService;
    private ChallengeMessageService challengeMessageService;

    @Autowired
    public DaoStatusCheckService(ChallengeDao challengeDao, FlickrStatusCheckService flickrStatusCheckService,
                                 SimpleMailService mailService, ChallengeMessageService challengeMessageService) {
        this.challengeDao = challengeDao;
        this.flickrStatusCheckService = flickrStatusCheckService;
        this.mailService = mailService;
        this.challengeMessageService = challengeMessageService;
    }

    public Set<ImageItemStatus> checkSearch(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        Set<ImageItemStatus> results = new HashSet<ImageItemStatus>();

        for (FlickrImageStatus status : flickrStatusCheckService.checkSearch(tag, challenge.getStartDate())) {
            results.add(new ImageItemStatusInstance(status));
        }

        return results;
    }

    public ChallengeSummary warnForCurrent() {
        Challenge challenge = challengeDao.getCurrentChallenge();

        if (challenge == null) {
            if (logger.isInfoEnabled()) {
                logger.info("No challenge found to check");
            }

            return null;
        }

        Set<ImageItemStatus> status = checkSearch(challenge.getTag());

        if (!status.isEmpty()) {
            mailService.sendPost(challengeMessageService.getWarnForCurrentTitle(challenge.getTag()),
                    challengeMessageService.getWarnForCurrentBody(status));
        }

        return new ChallengeSummaryInstance(challenge);
    }
}
