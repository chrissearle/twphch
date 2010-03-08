/*
 * Copyright 2009 Chris Searle
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

import net.chrissearle.flickrvote.web.model.Image;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FancyboxImageTag extends TagSupport {
    private static final long serialVersionUID = -3442973149583766220L;

    private transient Logger logger = Logger.getLogger(FancyboxImageTag.class);

    private Image image;

    private Integer position;

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

            text.append("<a style=\"display: none\" id=\"fb_");
            text.append(position);
            text.append("\" class=\"fancyboxImage\"></a>");

            text.append("<div style=\"display: none\"><a id=\"img_fb_");
            text.append(position);
            text.append("\" rel=\"group\" class=\"flickrImage\" title=\"");
            text.append(image.getImageTitle());
            text.append("\" href=\"");
            text.append(image.getImageUrl());
            text.append("\">");

            text.append("<img src=\"");
            text.append(image.getImageUrl());
            text.append("\" alt=\"");
            text.append(image.getImageTitle());
            text.append("\"/>");

            text.append("</a></div>");

            out.print(text.toString());
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to generate image", e);
            }
        }

        return super.doStartTag();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}