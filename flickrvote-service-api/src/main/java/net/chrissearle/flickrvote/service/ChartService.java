package net.chrissearle.flickrvote.service;

import org.jfree.chart.JFreeChart;

public interface ChartService {
    JFreeChart getChartForChallenge(String tag);

    JFreeChart getVotingChart();
}
