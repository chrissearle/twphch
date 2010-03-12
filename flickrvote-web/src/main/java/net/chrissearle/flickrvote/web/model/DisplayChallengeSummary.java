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

package net.chrissearle.flickrvote.web.model;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;

import java.util.Date;

public class DisplayChallengeSummary implements Challenge {
    private String tag;
    private String description;
    private Date start;
    private Date vote;
    private Date end;
    private Boolean open;
    private Boolean closed;
    private Boolean voting;
    private static final String TAG_PREFIX = "TwPhCh";
    private String notes;

    public DisplayChallengeSummary(ChallengeSummary summary) {
        tag = summary.getTag();
        description = summary.getTitle();
        notes = summary.getNotes();
        start = summary.getStartDate();
        vote = summary.getVoteDate();
        end = summary.getEndDate();
        open = summary.isOpen();
        closed = summary.isClosed();
        voting = summary.isVoting();
    }

    public String getChallengeTag() {
        return tag;
    }

    public String getChallengeDescription() {
        return description;
    }

    public String getChallengeNotes() {
        return notes;
    }

    public Date getChallengeStart() {
        return start == null ? null : new Date(start.getTime());
    }

    public Date getChallengeVote() {
        return vote == null ? null : new Date(vote.getTime());
    }

    public Date getChallengeEnd() {
        return end == null ? null : new Date(end.getTime());
    }

    public boolean isChallengeOpen() {
        return open;
    }

    public boolean isChallengeClosed() {
        return closed;
    }

    public boolean isChallengeVoting() {
        return voting;
    }

    public String getShortTag() {
        if (tag.indexOf(TAG_PREFIX) > -1) {
            return tag.substring(TAG_PREFIX.length());
        }
        return tag;
    }
}
