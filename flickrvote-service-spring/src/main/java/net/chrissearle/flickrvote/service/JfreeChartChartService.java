package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class JFreeChartChartService implements ChartService {
    private ChallengeService challengeService;

    @Autowired
    public JFreeChartChartService(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    public JFreeChart getChartForChallenge(String tag) {
        ChallengeInfo challenge = challengeService.getChallenge(tag);

        List<ImageInfo> images = challengeService.getImagesForChallenge(tag);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ImageInfo image : images) {
            dataset.setValue(image.getFinalVoteCount(), "Score", image.getPhotographerName());
        }

        JFreeChart chart = ChartFactory.createBarChart3D(
                challenge.getTag(),
                "Photographer",
                "Score",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(Math.PI / 6.0));

        GradientPaint gp = new GradientPaint(
                0.0f, 0.0f, Color.RED,
                0.0f, 0.0f, Color.ORANGE
        );

        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
        renderer.setSeriesPaint(0, gp);

        chart.setBackgroundPaint(java.awt.Color.white);

        return chart;
    }
}
