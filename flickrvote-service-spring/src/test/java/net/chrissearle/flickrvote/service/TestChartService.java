package net.chrissearle.flickrvote.service;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * This is not a real test. It exists simply to dump charts out to file for easier fiddling.
 */
public class TestChartService {
    ChartService chartService;
    ChallengeService challengeService;

    @BeforeClass
    public void setup() {
        challengeService = new TestChartChallengeService();
        chartService = new JFreeChartChartService(challengeService);
    }

    @Test
    public void testChart() throws IOException {
        JFreeChart chart = chartService.getChartForChallenge("TestChart");

        ChartUtilities.saveChartAsPNG(new File("/tmp/testchart.png"), chart, 500, 1000);
        ChartUtilities.saveChartAsJPEG(new File("/tmp/testchart.jpg"), chart, 500, 1000);
    }

}
