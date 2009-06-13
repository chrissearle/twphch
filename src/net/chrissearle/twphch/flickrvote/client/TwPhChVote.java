package net.chrissearle.twphch.flickrvote.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.*;

public class TwPhChVote implements EntryPoint {
    private TwPhChConstants constants = GWT.create(TwPhChConstants.class);
    private Widget mainTab = new HTML("main");
    private Widget historyTab = new HTML("history");
    private VerticalPanel adminTab = new VerticalPanel();
    private VoteServiceAsync voteService = GWT.create(VoteService.class);

    public void onModuleLoad() {
        Window.setTitle(constants.mainTitle());
        RootPanel.get("mainTitle").add(new Label(constants.mainTitle()));

        TabPanel mainTabs = new TabPanel();
        mainTabs.setWidth("600px");
        RootPanel.get().add(mainTabs);

        mainTabs.add(mainTab, constants.mainTabTitle());
        mainTabs.add(historyTab, constants.historyTabTitle());

        // Add admin
        populateAdminTab();
        mainTabs.add(adminTab, constants.adminTabTitle());
    }

    private void populateAdminTab() {
        final FlexTable adminTable = new FlexTable();

        adminTable.setText(0, 0, constants.columnVoteTitle());
        adminTable.setText(0, 1, constants.columnNameTitle());
        adminTable.setText(0, 2, constants.columnLinkTitle());


        // Initialize the service proxy.
        if (voteService == null) {
            voteService = GWT.create(VoteService.class);
        }

        // Set up the callback object.
        AsyncCallback<List<VotingRound>> callback = new AsyncCallback<List<VotingRound>>() {
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
            }

            public void onSuccess(List<VotingRound> result) {
                updateAdminTable(adminTable, result);
            }
        };

        // Make the call to the stock price service.
        voteService.getVotes(callback);

        adminTab.add(adminTable);
    }

    private void updateAdminTable(FlexTable table, List<VotingRound> votes) {
        for (VotingRound vote : votes) {
            int row = table.getRowCount() + 1;

            table.setText(row, 0, vote.getRound());
            table.setText(row, 1, vote.getName());
            table.setWidget(row, 2, new HTML("<a href='" + vote.getLink() + "'>" + vote.getRound() + "</a>"));
        }
    }
}
