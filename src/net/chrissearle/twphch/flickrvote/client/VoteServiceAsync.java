package net.chrissearle.twphch.flickrvote.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface VoteServiceAsync {
    void getVotes(AsyncCallback<List<VotingRound>> async);
}
