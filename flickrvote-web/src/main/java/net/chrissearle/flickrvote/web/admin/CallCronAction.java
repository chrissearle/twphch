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
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.StatusCheckService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

public class CallCronAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private PhotographyService photographyService;

    @Autowired
    private StatusCheckService statusCheckService;

    public String openVoting() throws Exception {
        photographyService.freezeChallenge();

        ChallengeSummary challenge = challengeService.openVoting();

        if (challenge != null) {
            addActionMessage("Voting opened for: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for voting");
        }

        return SUCCESS;
    }

    public String newChallenge() throws Exception {
        ChallengeSummary challenge = challengeService.announceNewChallenge();

        if (challenge != null) {
            addActionMessage("Announced new challenge: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for announcement");
        }

        return SUCCESS;
    }

    public String results() throws Exception {
        ChallengeSummary challenge = challengeService.announceResults();

        if (challenge != null) {
            addActionMessage("Results calculated for: " + challenge.getTag());
            addActionMessage("Badges added for: " + challenge.getTag());
            addActionMessage("Results announced for: " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for results");
        }

        return SUCCESS;
    }

    public String checkWarnCurrent() throws Exception {
        ChallengeSummary challenge = statusCheckService.warnForCurrent();

        if (challenge != null) {
            addActionMessage("Checked and mailed (if required) for : " + challenge.getTag());
        } else {
            addActionMessage("No challenge found for checking");
        }

        return SUCCESS;
    }
}
