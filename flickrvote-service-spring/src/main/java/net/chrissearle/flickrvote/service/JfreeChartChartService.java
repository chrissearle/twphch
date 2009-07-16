package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeInfo;
import net.chrissearle.flickrvote.service.model.ImageInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
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

        JFreeChart chart = ChartFactory.createBarChart(
                challenge.getTag(),
                "Photographer",
                "Score",
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(Math.PI / 6.0));

        final Color background = new Color(65, 65, 65);
        final Color foreground = new Color(213, 210, 214);
        final Color orange = new Color(255, 156, 39);


        GradientPaint gp = new GradientPaint(
                0.0f, 0.0f, background,
                0.0f, 0.0f, orange
        );

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, gp);
        renderer.setDrawBarOutline(false);
        renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));
        
        domainAxis.setLabelPaint(foreground);
        domainAxis.setTickLabelPaint(foreground);
        plot.getRangeAxis().setLabelPaint(foreground);
        plot.getRangeAxis().setTickLabelPaint(foreground);

        chart.setBackgroundPaint(background);
        chart.getTitle().setPaint(foreground);
        plot.setBackgroundPaint(background);
        plot.setDomainGridlinePaint(foreground);
        plot.setRangeGridlinePaint(foreground);


        return chart;
    }
}
