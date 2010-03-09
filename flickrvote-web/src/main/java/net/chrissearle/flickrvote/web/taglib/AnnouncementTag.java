package net.chrissearle.flickrvote.web.taglib;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AnnouncementTag extends TagSupport {
    Logger logger = Logger.getLogger(AnnouncementTag.class);
    
    @Override
    public int doStartTag() throws JspException {
        File announcementFile = new File("/etc/flickrvote/announcement.html");

        if (announcementFile.exists() && announcementFile.isFile()) {
            JspWriter out = pageContext.getOut();

            try {
                StringBuilder text = new StringBuilder();

                text.append("<div class='announce'>");
                text.append(getContents(announcementFile));
                text.append("</div>");

                out.print(text.toString());
            } catch (IOException e) {
                if (logger.isEnabledFor(Level.WARN)) {
                    logger.warn("Unable to generate announcement", e);
                }
            }
        }

        return super.doStartTag();
    }

    protected String getContents(File aFile) {
        StringBuilder contents = new StringBuilder();

        try {
            BufferedReader input = new BufferedReader(new FileReader(aFile));

            try {
                String line = null;

                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            }
            finally {
                input.close();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }

}