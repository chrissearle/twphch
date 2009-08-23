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

package net.chrissearle.flickrvote.scheduler;

import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class FreezeAndOpenVotingJob extends QuartzJobBean {
    private ChallengeService challengeService;

    private PhotographyService photographyService;

    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        photographyService.freezeChallenge();
        challengeService.openVoting();
    }

    public void setChallengeService(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    public void setPhotographyService(PhotographyService photographyService) {
        this.photographyService = photographyService;
    }
}
