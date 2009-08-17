package net.chrissearle.flickrvote.web.taglib;

import net.chrissearle.flickrvote.web.model.Image;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ImageTag extends TagSupport {
    private static final long serialVersionUID = -3442973149583766220L;

    private transient Logger logger = Logger.getLogger(ImageTag.class);

    private Image image;
    private Boolean showBadge = true;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Boolean isShowBadge() {
        return showBadge;
    }

    public void setShowBadge(Boolean showBadge) {
        this.showBadge = showBadge;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder text = new StringBuilder();

            if (showBadge) {
                String badge = null;
                if (image.getRank() == 1) {
                    badge = "gold";
                }
                if (image.getRank() == 2) {
                    badge = "silver";
                }
                if (image.getRank() == 3) {
                    badge = "bronze";
                }

                if (badge != null) {
                    text.append("<img src=\"/twitterphotochallenge/images/");
                    text.append(badge);
                    text.append(".jpg\" alt=\"");
                    text.append(badge);
                    text.append("\"/>");
                    text.append("<br/>");
                    text.append("<br/>");
                }
            }

            text.append("<a href=\"");
            text.append(image.getPageUrl());
            text.append("\">");

            text.append("<img src=\"");
            text.append(image.getImageUrl());
            text.append("\" alt=\"");
            text.append(image.getImageTitle());
            text.append("\"/>");

            text.append("</a>");

            out.print(text.toString());
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to generate image", e);
            }
        }

        return super.doStartTag();
    }
}