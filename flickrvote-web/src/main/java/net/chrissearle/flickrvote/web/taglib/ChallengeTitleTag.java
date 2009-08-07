package net.chrissearle.flickrvote.web.taglib;

import net.chrissearle.flickrvote.web.model.Challenge;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ChallengeTitleTag extends TagSupport {
    private Logger logger = Logger.getLogger(ChallengeTitleTag.class);

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