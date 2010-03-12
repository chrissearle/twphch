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
import net.chrissearle.flickrvote.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;

public class RankItAction extends ActionSupport {
    private String tag;

    @Autowired
    private ChallengeService challengeService;

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String execute() throws Exception {
        challengeService.rankChallenge(tag);

        addActionMessage("Challenge " + tag + " ranked.");
        return SUCCESS;
    }
}
