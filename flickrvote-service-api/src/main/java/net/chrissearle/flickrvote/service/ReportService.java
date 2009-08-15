package net.chrissearle.flickrvote.service;

public interface ReportService {
    void generateHistoryReport();

    byte[] getHistoryReport();
}
