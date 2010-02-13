/*
 * Copyright 2009 Chris Searle
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

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import net.chrissearle.flickrvote.service.model.ChallengeItem;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.service.model.ChallengeType;
import net.chrissearle.flickrvote.service.model.ImageItem;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("reportService")
public class ITextReportService implements ReportService {
    private Logger logger = Logger.getLogger(ITextReportService.class);

    private ChallengeService challengeService;
    private PhotographyService photographyService;
    private ChallengeMessageService challengeMessageService;
    private ChartService chartService;
    private String historyReportPath;

    private static final Font SECTION_FONT = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font SUBSECTION_FONT = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);

    @Autowired
    public ITextReportService(ChallengeMessageService challengeMessageService, ChallengeService challengeService,
                              ChartService chartService, ReportServiceConfiguration config, PhotographyService photographyService) {
        this.challengeMessageService = challengeMessageService;
        this.challengeService = challengeService;
        this.chartService = chartService;
        this.historyReportPath = config.getHistoryReportPath();
        this.photographyService = photographyService;
    }

    public void generateHistoryReport() {
        if (logger.isInfoEnabled()) {
            logger.info("Generating history report");
        }

        try {
            Document document = new Document();

            File tempFile = File.createTempFile("flickrvote_history", ".pdf");

            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(tempFile));

            setFileMetadata(pdfWriter);

            document.open();

            String reportTitle = challengeMessageService.getHistoryReportTitle();

            document.addTitle(reportTitle);

            document.add(new Paragraph(reportTitle, new Font(Font.TIMES_ROMAN, 36, Font.BOLD)));

            addChallenges(document);

            document.close();

            if (logger.isInfoEnabled()) {
                logger.info("Renaming " + tempFile.getAbsolutePath() + " to " + historyReportPath);
            }

            File pdfFile = new File(historyReportPath);
            if (pdfFile.exists()) {
                if (!pdfFile.delete()) {
                    if (logger.isEnabledFor(Level.WARN)) {
                        logger.warn("Could not delete file " + historyReportPath);
                    }
                }
            }

            try {
                FileUtils.moveFile(tempFile, pdfFile);
            } catch (IOException e) {
                if (logger.isEnabledFor(Level.WARN)) {
                    logger.warn("Could not rename file " + tempFile.getAbsolutePath() + " to " + historyReportPath, e);
                }
            }
        } catch (DocumentException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to create PDF", e);
            }
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to create PDF", e);
            }
        }
    }

    private void addChallenges(Document document) throws DocumentException {
        List<ChallengeSummary> challenges = new ArrayList<ChallengeSummary>(challengeService.getChallengesByType(ChallengeType.CLOSED));

        Collections.sort(challenges, new Comparators.ChallengeSummarySortByStartDateComparator());

        int chapterCount = 1;
        for (ChallengeSummary challenge : challenges) {
            addChallengeChapter(document, chapterCount++, challenge);
        }
    }

    private void addChallengeChapter(Document document, int chapterCount, ChallengeSummary challenge) throws DocumentException {
        Chapter challengeChapter = new Chapter(new Paragraph("#" + challenge.getTag(), new Font(Font.TIMES_ROMAN, 24, Font.BOLD)), chapterCount);
        challengeChapter.setNumberDepth(0);
        challengeChapter.setBookmarkOpen(true);
        challengeChapter.setBookmarkTitle("#" + challenge.getTag() + " " + challenge.getTitle());

        challengeChapter.add(new Paragraph(challenge.getTitle(), new Font(Font.TIMES_ROMAN, 20, Font.BOLDITALIC)));

        addInfoSection(challengeChapter, challenge);

        addImagesSection(challengeChapter, challenge);

        document.add(challengeChapter);
    }

    private void addImagesSection(Chapter challengeChapter, ChallengeSummary challenge) {
        challengeChapter.newPage();

        ChallengeItem challengeDetails = photographyService.getChallengeImages(challenge.getTag());

        List<ImageItem> images = new ArrayList<ImageItem>(challengeDetails.getImages());

        Collections.sort(images, new Comparators.ImageItemSortByVoteCount());

        String title = challengeMessageService.getImageSectionTitle();

        Section section = challengeChapter.addSection(new Paragraph(title, SECTION_FONT), 0);
        section.setBookmarkTitle(title);
        section.setBookmarkOpen(false);

        for (ImageItem image : images) {
            addImage(section, image);
        }
    }

    private void addImage(Section section, ImageItem image) {

        String title = image.getRank() + ": " + image.getTitle();
        Section subSection = section.addSection(new Paragraph(title, SUBSECTION_FONT), 0);
        subSection.setBookmarkOpen(true);
        subSection.setBookmarkTitle(title);

        try {
            Image img = Image.getInstance(new URL(image.getImageUrl()));
            img.setAlignment(Image.ALIGN_MIDDLE);
            subSection.add(img);

            float[] widths = {1f, 3f};
            PdfPTable table = new PdfPTable(widths);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.getDefaultCell().setPadding(5);
            table.setSpacingBefore(15);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            addTableRow(table, challengeMessageService.getHistoryImageTitleTitle(), image.getTitle());
            addTableRow(table, challengeMessageService.getHistoryImageRankTitle(), "" + image.getRank());
            addTableRow(table, challengeMessageService.getHistoryImageVoteTitle(), "" + image.getVoteCount());
            if (image.getPostedDate() != null) {
                addTableRow(table, challengeMessageService.getHistoryImagePostedTitle(), df.format(image.getPostedDate()));
            }
            addTableRow(table, challengeMessageService.getHistoryImageUrlTitle(), image.getUrl());
            addTableRow(table, challengeMessageService.getHistoryImagePhotographerTitle(), image.getPhotographer().getName());

            subSection.add(table);

            subSection.newPage();
        } catch (BadElementException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to add image: " + image, e);
            }
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to add image: " + image, e);
            }
        } catch (IllegalArgumentException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to add image: " + image, e);
            }
        }
    }

    private void addTableRow(PdfPTable table, String title, String data) {
        table.addCell(title);
        table.addCell(data);
    }

    private void addInfoSection(Chapter challengeChapter, ChallengeSummary challenge) {
        String title = challengeMessageService.getInfoSectionTitle();

        Section infoSection = challengeChapter.addSection(new Paragraph(title, SECTION_FONT), 0);
        infoSection.setBookmarkOpen(false);
        infoSection.setBookmarkTitle(title);

        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPadding(5);
        table.setSpacingBefore(15);
        table.setSpacingAfter(15);

        final Font tableCellFont = new Font(Font.TIMES_ROMAN, 14, Font.NORMAL);

        if (challenge.getNotes() != null && !"".equals(challenge.getNotes())) {
            table.addCell(new Paragraph(challenge.getNotes(), tableCellFont));
        }
        
        table.addCell(new Paragraph(challengeMessageService.getInfoStartDate(challenge.getStartDate()), tableCellFont));
        table.addCell(new Paragraph(challengeMessageService.getInfoVoteDate(challenge.getVoteDate()), tableCellFont));
        table.addCell(new Paragraph(challengeMessageService.getInfoEndDate(challenge.getEndDate()), tableCellFont));

        infoSection.add(table);

        try {
            Image img = Image.getInstance(new URL("http://vote.twphch.com/twitterphotochallenge/chart/showChart.action?tag=" + challenge.getTag()));
            img.setAlignment(Image.ALIGN_MIDDLE);
            img.scalePercent(50);
            infoSection.add(img);
        } catch (BadElementException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to add chart: " + challenge.getTag(), e);
            }
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.WARN)) {
                logger.warn("Unable to add chart: " + challenge.getTag(), e);
            }
        }
    }

    private void setFileMetadata(PdfWriter pdfWriter) {
        pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_5);

        pdfWriter.setViewerPreferences(PdfWriter.PageModeUseOutlines);

        pdfWriter.setStrictImageSequence(true);
    }

    public byte[] getHistoryReport() {
        long length = getHistoryReportSize();

        InputStream in = null;

        try {
            // Apparently arrays are max bound by int not long.
            if (length > 0 && length < Integer.MAX_VALUE) {

                in = new FileInputStream(new File(historyReportPath));

                byte[] bytes = new byte[(int) length];

                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length
                        && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
                    offset += numRead;
                }

                if (offset < bytes.length) {
                    if (logger.isEnabledFor(Level.ERROR)) {
                        logger.error("Failed to read history file");
                    }
                } else {
                    in.close();
                    return bytes;
                }
            }
        } catch (FileNotFoundException e) {
            if (logger.isEnabledFor(Level.ERROR)) {
                logger.error("Failed to find history file", e);
            }
        } catch (IOException e) {
            if (logger.isEnabledFor(Level.ERROR)) {
                logger.error("File error reading history file", e);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    if (logger.isEnabledFor(Level.WARN)) {
                        logger.warn("Unable to close stream in finally block");
                    }
                }
            }
        }
        return new byte[0];
    }

    public long getHistoryReportSize() {
        File file = new File(historyReportPath);

        if (!file.exists() || !file.canRead()) {
            return REPORT_UNAVAILABLE;
        }

        return file.length();
    }

}
