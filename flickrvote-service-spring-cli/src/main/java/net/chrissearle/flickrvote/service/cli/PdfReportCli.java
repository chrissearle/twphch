package net.chrissearle.flickrvote.service.cli;

import net.chrissearle.flickrvote.service.ReportService;

import java.io.IOException;

public class PdfReportCli extends AbstractCliService {
    public static void main(String[] args) throws IOException {
        PdfReportCli app = new PdfReportCli();
        app.initialize();

        app.generateReport();
    }

    private void generateReport() throws IOException {
        ReportService reportService = (ReportService) context.getBean("reportService");

        reportService.generateHistoryReport();
    }
}