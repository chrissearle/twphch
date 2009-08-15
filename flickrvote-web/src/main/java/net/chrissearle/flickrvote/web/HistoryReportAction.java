package net.chrissearle.flickrvote.web;

import com.opensymphony.xwork2.ActionSupport;
import net.chrissearle.flickrvote.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HistoryReportAction extends ActionSupport {
    private byte[] file;

    @Autowired
    private ReportService reportService;

    @Override
    public String execute() throws Exception {
        file = reportService.getHistoryReport();

        return SUCCESS;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(file);
    }

    public long getContentLength() {
        return file.length;
    }
}
