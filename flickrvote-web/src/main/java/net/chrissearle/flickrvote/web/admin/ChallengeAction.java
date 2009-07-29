package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class ChallengeAction extends ActionSupport {
    @Autowired
    ChallengeService challengeService;

    private List<ChallengeInfo> challenges;

    private String tag;

    private Boolean editFlag = false;

    private ChallengeInfo challenge;

    private static final int START_VOTE_TIME = 18;
    private static final int START_CHALLENGE_TIME = 18;
    private static final int END_CHALLENGE_TIME = 21;

    @Override
    public String input() throws Exception {
        if (tag != null && !"".equals(tag)) {
            editFlag = true;
            challenge = challengeService.getChallenge(tag);
        }

        return INPUT;
    }

    @Override
    public String execute() throws Exception {
        challenge.setStartDate(new DateTime(challenge.getStartDate()).plusHours(START_CHALLENGE_TIME).toDate());
        challenge.setVoteDate(new DateTime(challenge.getVoteDate()).plusHours(START_VOTE_TIME).toDate());
        challenge.setEndDate(new DateTime(challenge.getEndDate()).plusHours(END_CHALLENGE_TIME).toDate());

        challengeService.saveChallenge(challenge);

        return SUCCESS;
    }

    public String browse() {
        challenges = challengeService.getChallenges();

        Collections.sort(challenges);

        return "browse";
    }

    public List<ChallengeInfo> getChallenges() {
        return challenges;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public void setChallenge(ChallengeInfo challenge) {
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
