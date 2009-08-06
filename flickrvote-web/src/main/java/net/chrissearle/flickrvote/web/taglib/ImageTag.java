package net.chrissearle.flickrvote.web.taglib;

import net.chrissearle.flickrvote.web.model.Image;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ImageTag extends TagSupport {
    private Logger logger = Logger.getLogger(ImageTag.class);

    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder text = new StringBuilder();

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