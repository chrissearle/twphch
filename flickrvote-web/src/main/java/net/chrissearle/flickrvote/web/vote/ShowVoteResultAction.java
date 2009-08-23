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
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ShowVoteResultAction extends ActionSupport {

    @Autowired
    private ChallengeService challengeService;

    private Challenge challenge;

    @Override
    public String execute() throws Exception {
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        challenge = new DisplayChallengeSummary(challenges.iterator().next());

        return SUCCESS;
    }

    public Challenge getChallenge() {
        return challenge;
    }
}
