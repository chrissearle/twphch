package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.service.model.ImageItem;
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RankLabelGenerator extends AbstractCategoryItemLabelGenerator implements CategoryItemLabelGenerator {
    private static final long serialVersionUID = -4147924585049132201L;

    private Map<String, ImageItem> images;
    
    private String noImageText;

    public RankLabelGenerator(Set<ImageItem> images, String noImageText) {
        super("", NumberFormat.getInstance());

        this.images = new HashMap<String, ImageItem>();
        for (ImageItem image : images) {
            this.images.put(image.getChallenge().getTag(), image);
        }
        this.noImageText = noImageText;
    }

    public String generateLabel(CategoryDataset categoryDataset, int series, int category) {
        String label = noImageText;

        Comparable tag = categoryDataset.getColumnKey(category);

        if (images.containsKey(tag)) {
            label = images.get(tag).getRank().toString() + ".";
        }

        return label;
    }
}
