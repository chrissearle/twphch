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

package net.chrissearle.flickrvote.twitter;

import org.constretto.annotation.Configuration;
import org.constretto.annotation.Configure;
import twitter4j.Twitter;

public abstract class AbstractTwitter4JSupport {
    protected Twitter twitter;
    protected Boolean twitterActiveFlag;

    public AbstractTwitter4JSupport(Twitter twitter) {
        this.twitter = twitter;
    }

    @Configure
    public void configure(@Configuration(expression = "twitter.active") Boolean active) {
        twitterActiveFlag = active;
    }
}
