package net.chrissearle.twphch.flickrvote.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Date;

public interface VoteServiceAsync {

    void getVotes(AsyncCallback<List<VotingRound>> async);

    void addChallenge(String flickrUsername, String key, String name, Date startDate, Date stopDate, AsyncCallback<VotingRound> async);
}
