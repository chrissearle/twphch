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

package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChallengeListAction extends ActionSupport implements Preparable {
    @Autowired
    private ChallengeService challengeService;

    private List<Challenge> challenges;

    @Override
    public String execute() throws Exception {
        challenges = challenges.subList(0, 4);

        return SUCCESS;
    }

    public String browse() {
        return SUCCESS;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void prepare() throws Exception {
        challenges = new ArrayList<Challenge>();

        for (ChallengeSummary challenge : challengeService.getChallengesByType(ChallengeType.CLOSED)) {
            challenges.add(new DisplayChallengeSummary(challenge));
        }

        Collections.sort(challenges, new Comparator<Challenge>() {
            public int compare(Challenge o1, Challenge o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });
    }
}
