package net.chrissearle.twphch.flickrvote.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import java.util.*;

public class TwPhChVote implements EntryPoint {
    private TwPhChConstants constants = GWT.create(TwPhChConstants.class);
    private Widget mainTab = new HTML("main");
    private Widget historyTab = new HTML("history");
    private VerticalPanel adminTab = new VerticalPanel();
    private VoteServiceAsync voteService = GWT.create(VoteService.class);

    // Admin fields
    private TextBox challengeKey = new TextBox();
    private TextBox challengeName = new TextBox();
    private DatePicker votingDates = new DatePicker();

    private FlexTable adminTable = new FlexTable();

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
        adminTable.setText(0, 0, constants.columnVoteTitle());
        adminTable.setText(0, 1, constants.columnNameTitle());
        adminTable.setText(0, 2, constants.columnLinkTitle());

        // Initialize the service proxy.
        if (voteService == null) {
            voteService = GWT.create(VoteService.class);
        }

        getVotes();

        adminTab.add(adminTable);

        VerticalPanel addChallengePanel = new VerticalPanel();

        adminTab.add(addChallengePanel);

        HorizontalPanel keyPanel = new HorizontalPanel();

        keyPanel.add(new Label(constants.challengeKeyForm()));
        keyPanel.add(challengeKey);

        addChallengePanel.add(keyPanel);

        HorizontalPanel namePanel = new HorizontalPanel();

        namePanel.add(new Label(constants.challengeNameForm()));
        namePanel.add(challengeName);

        addChallengePanel.add(namePanel);

        HorizontalPanel datePanel = new HorizontalPanel();

        datePanel.add(new Label(constants.challengeDateForm()));
        datePanel.add(votingDates);

        addChallengePanel.add(datePanel);

        Button addChallengeButton = new Button(constants.challengeAddButton());
        addChallengePanel.add(addChallengeButton);

        addChallengeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addChallenge();
            }
        });
    }

    private void getVotes() {
        // Set up the getVotesCallback object.
        AsyncCallback<List<VotingRound>> getVotesCallback = new AsyncCallback<List<VotingRound>>() {
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
            }

            public void onSuccess(List<VotingRound> result) {
                updateAdminTable(result);
            }
        };

        voteService.getVotes(getVotesCallback);
    }

    private void updateAdminTable(List<VotingRound> votes) {
        for (VotingRound vote : votes) {
            addRoundToAdminTable(vote.getId(), vote.getRound(), vote.getName(), vote.getLink());
        }
    }

    private void addRoundToAdminTable(Long id, final String round, String name, String link) {
        int row = adminTable.getRowCount() + 1;

        adminTable.setText(row, 0, round);
        adminTable.setText(row, 1, name);
        adminTable.setWidget(row, 2, new HTML("<a href='" + link + "'>" + round + "</a>"));
        Button deleteButton = new Button(constants.deleteButton());

        deleteButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // TODO - delete from db and delete from table. How to find correct row? How to know ID at this point?
            }
        });

        adminTable.setWidget(row, 3, deleteButton);
    }

    private void addChallenge() {
        final String key = challengeKey.getText().toUpperCase().trim();
        final String name = challengeName.getText().trim();
        final Date start = votingDates.getFirstDate();
        final Date stop = votingDates.getLastDate();

        // Initialize the service proxy.
        if (voteService == null) {
            voteService = GWT.create(VoteService.class);
        }

        AsyncCallback<VotingRound> addChallengeCallback = new AsyncCallback<VotingRound>() {
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
            }

            public void onSuccess(VotingRound vote) {
                addRoundToAdminTable(vote.getId(), vote.getRound(), vote.getName(), vote.getLink());
            }
        };

        voteService.addChallenge("", key, name, start, stop, addChallengeCallback);
    }
}
