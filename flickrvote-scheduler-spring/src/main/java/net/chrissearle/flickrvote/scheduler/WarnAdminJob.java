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

package net.chrissearle.flickrvote.scheduler;

import net.chrissearle.flickrvote.service.AdministratorService;
import net.chrissearle.flickrvote.service.ChallengeMessageService;
import net.chrissearle.flickrvote.service.ChallengeSummaryService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import net.chrissearle.flickrvote.twitter.DirectMessageService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Set;

public class WarnAdminJob extends QuartzJobBean {
    private ChallengeSummaryService challengeSummaryService;
    private ChallengeMessageService challengeMessageService;
    private AdministratorService administratorService;
    private DirectMessageService dmService;
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (getChallengesOpenAt1900().size() == 0) {
            warnAdmins();
        }
    }

    private void warnAdmins() {
        String warning = challengeMessageService.getNoChallengeWarning();

        for (PhotographerItem photographer : administratorService.getAdmins()) {
            warnAdmin(warning, photographer.getTwitter());
        }
    }

    private void warnAdmin(final String warning, final String twitterAccount) {
        if (twitterAccount != null && !"".equals(twitterAccount)) {
            if (logger.isInfoEnabled()) {
                logger.info("DM send to " + twitterAccount + " message " + warning);
            }

            dmService.dm(twitterAccount, warning);
        }
    }

    private Set<ChallengeSummary> getChallengesOpenAt1900() {
        DateTime now = new DateTime();
        DateTime afterOpeningToday = now.hourOfDay().setCopy(19);

        return challengeSummaryService.getChallengesOpenAt(afterOpeningToday.toDate());
    }

    public void setChallengeSummaryService(ChallengeSummaryService challengeSummaryService) {
        this.challengeSummaryService = challengeSummaryService;
    }

    public void setChallengeMessageService(ChallengeMessageService challengeMessageService) {
        this.challengeMessageService = challengeMessageService;
    }

    public void setDmService(DirectMessageService dmService) {
        this.dmService = dmService;
    }

    public void setAdministratorService(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }
}
