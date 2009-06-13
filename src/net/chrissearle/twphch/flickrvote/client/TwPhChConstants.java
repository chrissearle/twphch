package net.chrissearle.twphch.flickrvote.client;

import com.google.gwt.i18n.client.Constants;

public interface TwPhChConstants extends Constants {

    @DefaultStringValue("#TwPhCh Voting")
    String mainTitle();

    @DefaultStringValue("Voting")
    String mainTabTitle();

    @DefaultStringValue("Previous Votes")
    String historyTabTitle();

    @DefaultStringValue("Administration")
    String adminTabTitle();

    @DefaultStringValue("Vote")
    String columnVoteTitle();

    @DefaultStringValue("Name")
    String columnNameTitle();

    @DefaultStringValue("Flickr Link")
    String columnLinkTitle();
}
