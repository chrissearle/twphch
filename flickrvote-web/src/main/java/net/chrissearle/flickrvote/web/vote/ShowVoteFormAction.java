package net.chrissearle.flickrvote.web.vote;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import net.chrissearle.flickrvote.service.model.PhotographerInfo;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ShowVoteFormAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private ChallengeInfo challenge;

    @Autowired
    private ChallengeService challengeService;

    private List<ImageInfo> images;

    public void setSession(Map<String, Object> stringObjectMap) {
        this.session = stringObjectMap;
    }

    @Override
    public String execute() throws Exception {
        if (session.containsKey("flickrUser")) {
            PhotographerInfo photographer = (PhotographerInfo) session.get("flickrUser");

            if (challengeService.hasVoted(photographer.getId())) {
                return "alreadyVoted";
            }
        }

        challenge = challengeService.getVotingChallenge();

        if (challenge != null) {
            images = challengeService.getImagesForChallenge(challenge.getTag());
        }
        
        return SUCCESS;
    }

    public ChallengeInfo getChallenge() {
        return challenge;
    }

    public List<ImageInfo> getImages() {
        return images;
    }
}
