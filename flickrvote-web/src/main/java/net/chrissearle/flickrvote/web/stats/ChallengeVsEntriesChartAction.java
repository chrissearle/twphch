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

package net.chrissearle.flickrvote.web.stats;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import net.chrissearle.flickrvote.web.model.ImageStatistic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChallengeVsEntriesChartAction extends ActionSupport {
    private List<Challenge> challenges;

    @Autowired
    private ChallengeService challengeService;

    private long height;

    private List<ImageStatistic> imageCounts;

    private List<ImageStatistic> imageVoteCounts;

    @Autowired
    private PhotographyService photographyService;

    public String stats() throws Exception {
        this.challenges = new ArrayList<Challenge>();
        this.imageCounts = new ArrayList<ImageStatistic>();
        this.imageVoteCounts = new ArrayList<ImageStatistic>();

        for (ChallengeSummary challenge : challengeService.getChallengesByType(ChallengeType.CLOSED)) {
            challenges.add(new DisplayChallengeSummary(challenge));

            final ChallengeItem challengeItem = photographyService.getChallengeImages(challenge.getTag());

            imageCounts.add(new ImageStatistic(challenge.getTag(), challengeItem.getDescription(), (float) challengeItem.getImages().size()));

            Long voteCount = 0L;

            for (ImageItem imageItem : challengeItem.getImages()) {
                voteCount += imageItem.getVoteCount();
            }

            imageVoteCounts.add(new ImageStatistic(challenge.getTag(), challengeItem.getDescription(), (float) voteCount));
        }

        Collections.sort(this.challenges, new Comparator<Challenge>() {
            public int compare(Challenge o1, Challenge o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });

        Collections.sort(this.imageCounts, new Comparator<ImageStatistic>() {
            public int compare(ImageStatistic o1, ImageStatistic o2) {
                return o2.getTag().compareTo(o1.getTag());
            }
        });

        Collections.sort(this.imageVoteCounts, new Comparator<ImageStatistic>() {
            public int compare(ImageStatistic o1, ImageStatistic o2) {
                return o2.getTag().compareTo(o1.getTag());
            }
        });

        this.height = (long) (30 * challenges.size()) + 140;

        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public List<Challenge> getChallenges() {
        return this.challenges;
    }

    public Long getChallengeVsEntriesHeight() {
        return this.height;
    }

    public List<ImageStatistic>getImageCounts() {
        return this.imageCounts;
    }

    public List<ImageStatistic>getImageVoteCounts() {
        return this.imageVoteCounts;
    }
}
