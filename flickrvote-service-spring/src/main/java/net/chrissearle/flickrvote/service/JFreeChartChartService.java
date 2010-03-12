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

package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service("chartService")
public class JFreeChartChartService implements ChartService {
    public static final Color background = new Color(65, 65, 65);
    public static final Color foreground = new Color(213, 210, 214);
    public static final Color orange = new Color(255, 156, 39);


    private ChallengeService challengeService;
    private PhotographyService photographyService;

    @Autowired
    public JFreeChartChartService(ChallengeService challengeService, PhotographyService photographyService) {
        this.challengeService = challengeService;
        this.photographyService = photographyService;
    }

    public JFreeChart getChartForChallenge(String tag, String scoreAxisTitle, String photographerAxisTitle) {
        ChallengeSummary challengeSummary = challengeService.getChallengeSummary(tag);

        ChallengeItem challenge = photographyService.getChallengeImages(tag);

        List<ImageItem> images = new ArrayList<ImageItem>(challenge.getImages().size());
        images.addAll(challenge.getImages());

        Collections.sort(images, new Comparators.ImageItemSortByVoteCount());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ImageItem image : images) {
            dataset.setValue(image.getVoteCount(), scoreAxisTitle, image.getPhotographer().getName());
        }

        return generateChart(challengeSummary.getTag(), challengeSummary.getTitle(), dataset, scoreAxisTitle, photographerAxisTitle);
    }

    private JFreeChart generateChart(String tag, String subtitle, CategoryDataset dataset, String scoreAxisTitle, String photographerAxisTitle) {
        JFreeChart chart = ChartFactory.createBarChart(
                tag,
                photographerAxisTitle,
                scoreAxisTitle,
                dataset,
                PlotOrientation.HORIZONTAL,
                false,
                true,
                false
        );

        List<Title> subtitles = new ArrayList<Title>(1);
        TextTitle title = new TextTitle(subtitle);
        title.setPaint(foreground);
        subtitles.add(title);

        chart.setSubtitles(subtitles);

        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createDownRotationLabelPositions(Math.PI / 6.0));


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

    public JFreeChart getVotingChart(String scoreAxisTitle, String photographerAxisTitle) {
        Set<ChallengeSummary> challenges = challengeService.getChallengesByType(ChallengeType.VOTING);

        // Currently only support one voting challenge in the front end
        ChallengeSummary challengeSummary = challenges.iterator().next();

        ChallengeItem challenge = photographyService.getChallengeImages(challengeSummary.getTag());

        List<ImageItem> images = new ArrayList<ImageItem>(challenge.getImages().size());
        images.addAll(challenge.getImages());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ImageItem image : images) {
            dataset.setValue(image.getVoteCount(), scoreAxisTitle, image.getPhotographer().getName());
        }

        return generateChart(challengeSummary.getTag(), challengeSummary.getTitle(), dataset, scoreAxisTitle, photographerAxisTitle);
    }

    public JFreeChart getChartForPhotographer(String id, String rankAxisTitle, String challengeAxisTitle, String noImageText) {
        Set<ImageItem> images = photographyService.getImagesForPhotographer(id);

        List<ChallengeSummary> challenges = new ArrayList<ChallengeSummary>();

        challenges.addAll(challengeService.getChallengesByType(ChallengeType.CLOSED));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, ImageItem> imageMap = new HashMap<String, ImageItem>();
        String photographerName = null;

        for (ImageItem image : images) {
            imageMap.put(image.getChallenge().getTag(), image);
            if (photographerName == null) {
                photographerName = image.getPhotographer().getName();
            }
        }

        Collections.sort(challenges, new Comparators.ChallengeSummarySortByTag());

        for (ChallengeSummary challenge : challenges) {
            if (imageMap.containsKey(challenge.getTag())) {
                dataset.setValue(imageMap.get(challenge.getTag()).getVoteCount(), rankAxisTitle, challenge.getTag());
            } else {
                dataset.setValue(0L, rankAxisTitle, challenge.getTag());
            }
        }

        JFreeChart chart = generateChart(photographerName == null ? "" : photographerName, "", dataset, rankAxisTitle, challengeAxisTitle);

        CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
        renderer.setBaseItemLabelGenerator(new RankLabelGenerator(images, noImageText));
        renderer.setBaseItemLabelPaint(foreground);
        renderer.setBaseItemLabelsVisible(true);

        return chart;
    }

}
