package net.chrissearle.flickrvote.web.taglib;

import net.chrissearle.flickrvote.web.model.Photographer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PhotographerTag extends TagSupport {
    private Logger logger = Logger.getLogger(PhotographerTag.class);

    private Photographer photographer;

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder text = new StringBuilder();

            if (photographer.getIconUrl() != null) {
                text.append("<img src=\"");
                text.append(photographer.getIconUrl());
                text.append("\"/>");
                text.append("&nbsp;");
            }

            text.append(photographer.getPhotographerName());

            out.print(text.toString());
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to generate photographer", e);
            }
        }

        return super.doStartTag();
    }
}
