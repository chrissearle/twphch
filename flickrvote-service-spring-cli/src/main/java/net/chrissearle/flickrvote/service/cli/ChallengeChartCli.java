/*
 * Copyright 2010 Chris Searle
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

package net.chrissearle.flickrvote.service.cli;

import net.chrissearle.flickrvote.service.ChartService;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.io.File;
import java.io.IOException;

public class ChallengeChartCli extends AbstractCliService {
    public static void main(String[] args) throws IOException {
        ChallengeChartCli app = new ChallengeChartCli();
        app.initialize();

        app.generateChart(args[0]);
    }

    private void generateChart(String tag) throws IOException {
        ChartService service = (ChartService) context.getBean("chartService");

        JFreeChart chart = service.getChartForChallenge(tag, "Votes", "Photographer");

        ChartUtilities.saveChartAsPNG(new File(tag + ".png"), chart, 500, 1000);
    }
}
