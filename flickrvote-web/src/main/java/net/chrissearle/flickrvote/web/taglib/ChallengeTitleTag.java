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

package net.chrissearle.flickrvote.web.taglib;

import net.chrissearle.flickrvote.web.model.Challenge;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ChallengeTitleTag extends TagSupport {
    private static final long serialVersionUID = -7133187698295844912L;

    private transient Logger logger = Logger.getLogger(ChallengeTitleTag.class);

    private Challenge challenge;

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder text = new StringBuilder();

            text.append("#");
            text.append(challenge.getChallengeTag());
            text.append(" - ");
            text.append(challenge.getChallengeDescription());

            out.print(text.toString());
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to generate challenge title", e);
            }
        }

        return super.doStartTag();
    }
}