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

package net.chrissearle.flickrvote.web;

public interface FlickrVoteWebConstants {
    String FLICKR_LOGIN_URL = "flickrLoginUrl";
    String CHALLENGE_LIST = "challengeList";
    String VOTING_CHALLENGE = "voting";

    String FLICKR_USER_SESSION_KEY = "flickrUser";
    int START_VOTE_TIME = 18;
    int START_CHALLENGE_TIME = 18;
    int END_CHALLENGE_TIME = 21;
    String TAGPREFIX = "TwPhCh";
}
