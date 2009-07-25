package net.chrissearle.flickrvote.service.cli;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public abstract class AbstractCliService {
    protected ApplicationContext context;

    protected void initialize() {
        context = new ClassPathXmlApplicationContext(getContextFiles());
    }

    protected String[] getContextFiles() {
        String[] contextFiles = new String[1];
        contextFiles[0] = "applicationContext.xml";

        return contextFiles;
    }
}
