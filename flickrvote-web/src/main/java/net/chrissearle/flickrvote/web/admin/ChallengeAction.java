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

package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import net.chrissearle.flickrvote.web.model.ChallengeAdmin;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class ChallengeAction extends ActionSupport {
    @Autowired
    ChallengeService challengeService;

    private List<ChallengeSummary> challenges;

    private String tag;

    private Boolean editFlag = false;

    private ChallengeAdmin challenge;

    @Override
    public String input() throws Exception {
        if (tag != null && !"".equals(tag)) {
            editFlag = true;
            ChallengeSummary challengeSummary = challengeService.getChallengeSummary(tag);

            challenge = new ChallengeAdmin();
            challenge.setTag(challengeSummary.getTag());
            challenge.setTitle(challengeSummary.getTitle());
            challenge.setStartDate(challengeSummary.getStartDate());
            challenge.setNotes(challengeSummary.getNotes());
        } else {
            // Set some defaults
            ChallengeSummary mostRecentChallenge = challengeService.getMostRecent();

            if (mostRecentChallenge != null) {
                challenge = new ChallengeAdmin();

                challenge.setStartDate(mostRecentChallenge.getVoteDate());

                int numIndex = mostRecentChallenge.getTag().indexOf(FlickrVoteWebConstants.TAGPREFIX)
                        + FlickrVoteWebConstants.TAGPREFIX.length();

                int tagNum = Integer.valueOf(mostRecentChallenge.getTag().substring(numIndex));

                StringBuilder sb = new StringBuilder();

                Formatter formatter = new Formatter(sb, Locale.getDefault());

                formatter.format(FlickrVoteWebConstants.TAGPREFIX + "%03d", tagNum + 1);

                challenge.setTag(sb.toString());
            }
        }

        return INPUT;
    }

    @Override
    public String execute() throws Exception {
        DateTime start = new DateTime(challenge.getStartDate());

        Date startDate = start.plusHours(FlickrVoteWebConstants.START_CHALLENGE_TIME).toDate();
        Date voteDate = start.plusDays(7).plusHours(FlickrVoteWebConstants.START_VOTE_TIME).toDate();
        Date endDate = start.plusDays(9).plusHours(FlickrVoteWebConstants.END_CHALLENGE_TIME).toDate();

        challengeService.saveChallenge(challenge.getTag(), challenge.getTitle(), challenge.getNotes(), startDate, voteDate, endDate);

        return SUCCESS;
    }

    public String browse() {
        challenges = new ArrayList<ChallengeSummary>(challengeService.getChallengesByType(ChallengeType.ALL));

        Collections.sort(challenges, new Comparator<ChallengeSummary>() {
            public int compare(ChallengeSummary o1, ChallengeSummary o2) {
                return o2.getTag().compareTo(o1.getTag());
            }
        });

        return "browse";
    }

    public List<ChallengeSummary> getChallenges() {
        return challenges;
    }

    public ChallengeAdmin getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeAdmin challenge) {
        this.challenge = challenge;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean isEditFlag() {
        return editFlag;
    }

    public String delete() throws Exception {
        addActionMessage("Challenge removed");

        challengeService.remove(challenge.getTag());

        return SUCCESS;
    }
}
