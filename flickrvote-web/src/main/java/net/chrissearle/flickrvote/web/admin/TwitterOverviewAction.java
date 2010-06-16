/*
 * Copyright 2010 Chris Searle
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

package net.chrissearle.flickrvote.web.admin;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import net.chrissearle.flickrvote.service.model.PhotographerItem;
import net.chrissearle.flickrvote.web.model.TwitterStatus;
import net.chrissearle.spring.twitter.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class TwitterOverviewAction extends ActionSupport {
    private transient Logger logger = Logger.getLogger(TwitterOverviewAction.class.getName());

    @Autowired
    private transient FollowService followService;

    @Autowired
    private transient PhotographyService photographyService;

    private List<TwitterStatus> statuses = new ArrayList<TwitterStatus>();

    @Override
    public String execute() throws Exception {
        List<PhotographerItem> photographerItemList = photographyService.getPhotographers();

        List<String> amFollowing = followService.amFollowing();

        List<String> followers = followService.followingMe();

        for (PhotographerItem item : photographerItemList) {
            final String twitterId = item.getTwitter();

            if (twitterId != null && !"".equals(twitterId)) {
                TwitterStatus status = new TwitterStatus(item.getName(), twitterId,
                        amFollowing.contains(twitterId), followers.contains(twitterId));

                statuses.add(status);
            }
        }

        Collections.sort(statuses, new Comparator<TwitterStatus>() {
            public int compare(TwitterStatus o1, TwitterStatus o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return SUCCESS;
    }

    public List<TwitterStatus> getStatusList() {
        return statuses;
    }
}
