/*
 * Copyright 2009 Chris Searle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;
import net.chrissearle.flickrvote.service.model.Status;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class MessageSourceChallengeMessageService implements ChallengeMessageService, MessageSourceAware, InitializingBean {
    private MessageSource messageSource;

    private ShortUrlService shortUrlService;

    private String votingUrl;
    private String votingUrlShort;
    private String currentUrlShort;
    private String rulesUrl;
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
        currentUrlShort = shortUrlService.shortenUrl(getUrlProperty("url.current"));
        rulesUrl = getUrlProperty("url.rules");

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

    public String getHistoryReportTitle() {
        return messageSource.getMessage("history.report.title", new Object[0], Locale.getDefault());
    }

    public String getInfoSectionTitle() {
        return messageSource.getMessage("history.report.info.title", new Object[0], Locale.getDefault());
    }

    public String getInfoStartDate(Date date) {
        Object[] params = new Object[1];
        params[0] = df.format(date);

        return messageSource.getMessage("history.report.info.start", params, Locale.getDefault());
    }

    public String getInfoVoteDate(Date date) {
        Object[] params = new Object[1];
        params[0] = df.format(date);

        return messageSource.getMessage("history.report.info.vote", params, Locale.getDefault());
    }

    public String getInfoEndDate(Date date) {
        Object[] params = new Object[1];
        params[0] = df.format(date);

        return messageSource.getMessage("history.report.info.end", params, Locale.getDefault());
    }

    public String getImageSectionTitle() {
        return messageSource.getMessage("history.report.image.title", new Object[0], Locale.getDefault());
    }

    public String getHistoryImagePhotographerTitle() {
        return messageSource.getMessage("history.report.image.photographer.title", new Object[0], Locale.getDefault());
    }

    public String getVotingOpenWarning(ChallengeSummary challenge) {
        Object[] params = new Object[4];

        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();
        params[2] = df.format(challenge.getVoteDate());
        params[3] = currentUrlShort;

        return messageSource.getMessage("voting.open.warn", params, Locale.getDefault());
    }

    public String getVotingCloseWarning(ChallengeSummary challenge) {
        Object[] params = new Object[4];

        params[0] = challenge.getTag();
        params[1] = challenge.getTitle();
        params[2] = df.format(challenge.getEndDate());
        params[3] = votingUrlShort;

        return messageSource.getMessage("voting.close.warn", params, Locale.getDefault());
    }

    public String getWarnForCurrentTitle(String tag) {
        Object[] params = new Object[1];

        params[0] = tag;

        return messageSource.getMessage("warn.current.title", params, Locale.getDefault());
    }

    public String getWarnForCurrentBody(Set<ImageItemStatus> status) {
        StringBuffer message = new StringBuffer();

        for (ImageItemStatus itemStatus : status) {
            StringBuffer imageLinks = new StringBuffer();

            String sep = "";
            for (ImageItem image : itemStatus.getImages()) {
                imageLinks.append(sep);
                imageLinks.append(image.getUrl());
                sep = ", ";
            }

            Object[] params = new Object[2];

            params[0] = itemStatus.getStatus().getDisplayTitle();
            params[1] = imageLinks.toString();
            
            message.append(messageSource.getMessage("warn.current.line", params, Locale.getDefault()));
        }

        return message.toString();
    }

    public String generateFrontPageHtml(List<ImageItem> images) {
        StringBuffer message = new StringBuffer();

        for (ImageItem item : images) {
            Object[] params = new Object[5];

            params[0] = item.getPhotographer().getFullname() == null || "".equals(item.getPhotographer().getFullname()) ?
                    item.getPhotographer().getUsername() :
                    item.getPhotographer().getFullname();
            params[1] = item.getChallenge().getTag();
            params[2] = item.getChallenge().getTitle();
            params[3] = item.getUrl();
            params[4] = item.getImageUrl();

            message.append(messageSource.getMessage("frontpage.line", params, Locale.getDefault()));
        }

        return message.toString();
    }

    public String getFrontPageTitle() {
        return messageSource.getMessage("frontpage.title", new Object[0], Locale.getDefault());
    }

    public String getHistoryImagePostedTitle() {
        return messageSource.getMessage("history.report.image.posted.title", new Object[0], Locale.getDefault());
    }

    public String getHistoryImageRankTitle() {
        return messageSource.getMessage("history.report.image.rank.title", new Object[0], Locale.getDefault());
    }

    public String getHistoryImageTitleTitle() {
        return messageSource.getMessage("history.report.image.title.title", new Object[0], Locale.getDefault());
    }

    public String getHistoryImageUrlTitle() {
        return messageSource.getMessage("history.report.image.url.title", new Object[0], Locale.getDefault());
    }

    public String getHistoryImageVoteTitle() {
        return messageSource.getMessage("history.report.image.vote.title", new Object[0], Locale.getDefault());
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
