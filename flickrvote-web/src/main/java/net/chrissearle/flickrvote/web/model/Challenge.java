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

package net.chrissearle.flickrvote.web.model;

import java.io.Serializable;
import java.util.Date;

public interface Challenge extends Serializable {
    String getChallengeTag();

    String getChallengeDescription();

    String getChallengeNotes();

    Date getChallengeStart();

    Date getChallengeVote();

    Date getChallengeEnd();

    boolean isChallengeOpen();

    boolean isChallengeClosed();

    boolean isChallengeVoting();
}
