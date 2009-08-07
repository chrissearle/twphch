package net.chrissearle.flickrvote.web.model;

import java.util.Date;

public interface Challenge {
    String getChallengeTag();

    String getChallengeDescription();

    Date getChallengeStart();

    Date getChallengeVote();

    Date getChallengeEnd();

    boolean isChallengeOpen();

    boolean isChallengeClosed();

    boolean isChallengeVoting();
}
