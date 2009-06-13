package net.chrissearle.twphch.flickrvote.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.chrissearle.twphch.flickrvote.client.VoteService;
import net.chrissearle.twphch.flickrvote.client.VotingRound;
import net.chrissearle.twphch.flickrvote.server.model.Challenge;

import javax.jdo.PersistenceManager;
import java.util.*;

public class VoteServiceImpl extends RemoteServiceServlet implements VoteService {
    private static final String GROUP_URL = "http://www.flickr.com/groups/twphch/";

    // FIXME - these need to become a configuration
    private static final int VOTING_START_HOUR = 17;
    private static final int VOTING_START_MINUTE = 0;
    private static final int VOTING_STOP_HOUR = 21;
    private static final int VOTING_STOP_MINUTE = 0;

    public List<VotingRound> getVotes() {
        List<VotingRound> votes = new ArrayList<VotingRound>();

        PersistenceManager pm = PMF.get().getPersistenceManager();
        String query = "select from " + Challenge.class.getName();
        @SuppressWarnings("unchecked")
        List<Challenge> challenges = (List<Challenge>) pm.newQuery(query).execute();

        for (Challenge challenge : challenges) {
            VotingRound votingRound = new VotingRound(challenge.getChallengeKey(),
                    challenge.getChallengeName(),
                    GROUP_URL, challenge.getStartDate(), challenge.getStopDate());

            votingRound.setId(challenge.getId());

            votes.add(votingRound);
        }

        return votes;
    }

    public VotingRound addChallenge(String flickrUsername, String key, String name, Date startDate, Date stopDate) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(startDate);
        cal.set(Calendar.HOUR, VOTING_START_HOUR);
        cal.set(Calendar.MINUTE, VOTING_START_MINUTE);
        cal.set(Calendar.SECOND, 0);
        startDate = cal.getTime();

        cal.setTime(stopDate);
        cal.set(Calendar.HOUR, VOTING_STOP_HOUR);
        cal.set(Calendar.MINUTE, VOTING_STOP_MINUTE);
        cal.set(Calendar.SECOND, 0);
        stopDate = cal.getTime();

        Challenge newChallenge = new Challenge(flickrUsername, key, name, startDate, stopDate);

        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {
            // TODO - add flickr forum post with ink back to app

            pm.makePersistent(newChallenge);

            VotingRound votingRound = new VotingRound(newChallenge.getChallengeKey(), newChallenge.getChallengeName(), GROUP_URL,
                    newChallenge.getStartDate(), newChallenge.getStopDate());

            votingRound.setId(newChallenge.getId());

            return votingRound;
        } finally {
            pm.close();
        }
    }
}