package net.chrissearle.flickrvote.web.common;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.ReportService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.model.Challenge;
import net.chrissearle.flickrvote.web.model.DisplayChallengeSummary;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChallengeListBlockAction extends ActionSupport {
    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private ReportService reportService;

    private List<Challenge> challenges;

    private Long reportLength;

    @Override
    public String execute() throws Exception {
        challenges = new ArrayList<Challenge>();

        for (ChallengeSummary challenge : challengeService.getChallengesByType(ChallengeType.CLOSED)) {
            challenges.add(new DisplayChallengeSummary(challenge));
        }

        Collections.sort(challenges, new Comparator<Challenge>() {

            public int compare(Challenge o1, Challenge o2) {
                return o2.getChallengeTag().compareTo(o1.getChallengeTag());
            }
        });

        reportLength = reportService.getHistoryReportSize();

        return SUCCESS;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public long getReportLength() {
        // Return length in megabytes
        return (reportLength / (1024 * 1024));
    }

    public Boolean isReportAvailable() {
        return !(reportLength == ReportService.REPORT_UNAVAILABLE);
    }
}
