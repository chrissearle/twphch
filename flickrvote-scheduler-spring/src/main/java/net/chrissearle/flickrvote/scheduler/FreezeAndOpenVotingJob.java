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
