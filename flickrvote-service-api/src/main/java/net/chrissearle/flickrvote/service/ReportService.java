package net.chrissearle.flickrvote.service;

public interface ReportService {
    long REPORT_UNAVAILABLE = -1;

    void generateHistoryReport();

    byte[] getHistoryReport();

    long getHistoryReportSize();
}
