package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;

import java.util.Date;
import java.util.Set;

public interface ChallengeSummaryService {
    public Set<ChallengeSummary> getChallengesOpenAt(Date date);
}
