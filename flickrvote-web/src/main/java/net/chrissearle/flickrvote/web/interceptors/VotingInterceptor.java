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

package net.chrissearle.flickrvote.web.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class VotingInterceptor implements Interceptor {

    @Autowired
    ChallengeService challengeService;

    @Override
    public void init() {
    }

    @Override
    public void destroy() {
    }


    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if (challengeService.getChallengesByType(ChallengeType.VOTING).size() == 0) {
            Map<String, Object> session = actionInvocation.getInvocationContext().getSession();

            session.put(FlickrVoteWebConstants.VOTING_CLOSED_FLAG, true);

            return "votingclosed";
        }

        return actionInvocation.invoke();
    }
}
