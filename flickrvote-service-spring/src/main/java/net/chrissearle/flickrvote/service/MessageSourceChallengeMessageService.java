package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
public class MessageSourceChallengeMessageService implements ChallengeMessageService, MessageSourceAware, InitializingBean {
    private MessageSource messageSource;

    private ShortUrlService shortUrlService;

    private String votingUrl;
    private String votingUrlShort;
    private String currentUrl;
    private String currentUrlShort;
    private String rulesUrl;
    private String rulesUrlShort;
    private String goldBadgeUrl;
    private String silverBadgeUrl;
    private String bronzeBadgeUrl;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public MessageSourceChallengeMessageService(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void afterPropertiesSet() throws Exception {
        votingUrl = getUrlProperty("url.voting");
        votingUrlShort = shortUrlService.shortenUrl(votingUrl);
        currentUrl = getUrlProperty("url.current");
        currentUrlShort = shortUrlService.shortenUrl(currentUrl);
        rulesUrl = getUrlProperty("url.rules");
        rulesUrlShort = shortUrlService.shortenUrl(rulesUrl);

        goldBadgeUrl = getUrlProperty("gold.badge");
        silverBadgeUrl = getUrlProperty("silver.badge");
        bronzeBadgeUrl = getUrlProperty("bronze.badge");
    }

    private String getUrlProperty(String urlKey) {
        Object[] params = new Object[0];

        return messageSource.getMessage(urlKey, params, Locale.getDefault());
    }

    public String getResultsForumText(String resultsUrl, String firstPlace, String secondPlace, String thirdPlace) {
        Object[] params = new Object[4];

        params[0] = firstPlace;
        params[1] = secondPlace;
        params[2] = thirdPlace;
        params[3] = resultsUrl;

        return messageSource.getMessage("flickr.forum.results.text", params, Locale.getDefault());
    }

    public String getResultsUrl(ChallengeSummary challenge) {
        Object[] params = new Object[1];
        params[0] = challenge.getTag();

        return messageSource.getMessage("url.show", params, Locale.getDefault());
    }

    public String getVotingTwitter(ChallengeSummary challenge) {
        Object[] params = new Object[3];
        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();
        params[2] = votingUrlShort;

        return messageSource.getMessage("twitter.voting", params, Locale.getDefault());
    }

    public String getVotingForumTitle(ChallengeSummary challenge) {
        Object[] params = new Object[2];
        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();

        return messageSource.getMessage("flickr.forum.voting.title", params, Locale.getDefault());
    }

    public String getVotingForumText(ChallengeSummary challenge) {
        Object[] params = new Object[5];
        params[0] = df.format(challenge.getVoteDate());
        params[1] = df.format(challenge.getEndDate());
        params[2] = challenge.getTag();
        params[3] = challenge.getTitle();
        params[4] = votingUrl;

        return messageSource.getMessage("flickr.forum.voting.text", params, Locale.getDefault());
    }

    public String getCurrentTwitter(ChallengeSummary challenge) {
        Object[] params = new Object[3];
        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();
        params[2] = currentUrlShort;

        return messageSource.getMessage("twitter.current", params, Locale.getDefault());
    }

    public String getCurrentForumTitle(ChallengeSummary challenge) {
        Object[] params = new Object[2];
        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();

        return messageSource.getMessage("flickr.forum.current.title", params, Locale.getDefault());
    }

    public String getCurrentForumText(ChallengeSummary challenge) {
        Object[] params = new Object[5];
        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();
        params[2] = df.format(challenge.getVoteDate());
        params[3] = rulesUrl;

        return messageSource.getMessage("flickr.forum.current.text", params, Locale.getDefault());
    }

    public String getResultsTwitter(ChallengeSummary challenge, String resultsUrl) {
        resultsUrl = shortUrlService.shortenUrl(resultsUrl);

        Object[] params = new Object[3];
        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();
        params[2] = resultsUrl;

        return messageSource.getMessage("twitter.results", params, Locale.getDefault());
    }

    public String getResultsForumTitle(ChallengeSummary challenge) {
        Object[] params = new Object[2];
        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();

        return messageSource.getMessage("flickr.forum.results.title", params, Locale.getDefault());
    }


    public String getBadgeText(int place, String badgeUrl, ChallengeSummary challenge) {
        Object[] params = new Object[4];

        params[0] = place;
        params[1] = challenge.getTag();
        params[2] = challenge.getTitle();
        params[3] = badgeUrl;

        return messageSource.getMessage("badge.basic", params, Locale.getDefault());
    }

    public String getResultsForumSingle(ImageItem image) {
        Object[] params = new Object[5];

        params[0] = image.getTitle();
        params[1] = image.getPhotographer().getName();
        params[2] = image.getVoteCount();
        params[3] = image.getUrl();
        params[4] = image.getImageUrl();

        return messageSource.getMessage("flickr.forum.results.single", params, Locale.getDefault());
    }

    public String getGoldBadgeUrl() {
        return goldBadgeUrl;
    }

    public String getSilverBadgeUrl() {
        return silverBadgeUrl;
    }

    public String getBronzeBadgeUrl() {
        return bronzeBadgeUrl;
    }
}
