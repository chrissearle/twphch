package net.chrissearle.twphch.flickrvote.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import java.util.List;

@RemoteServiceRelativePath("VoteService")
public interface VoteService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use VoteService.App.getInstance() to access static instance of VoteServiceAsync
     */
    public static class App {
        private static final VoteServiceAsync ourInstance = (VoteServiceAsync) GWT.create(VoteService.class);

        public static VoteServiceAsync getInstance() {
            return ourInstance;
        }
    }

    List<VotingRound> getVotes();
}
