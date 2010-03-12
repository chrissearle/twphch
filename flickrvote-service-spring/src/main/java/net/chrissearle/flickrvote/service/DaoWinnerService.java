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

import net.chrissearle.flickrvote.dao.ImageDao;
import net.chrissearle.flickrvote.model.Image;
import net.chrissearle.flickrvote.service.model.ImageItem;
import net.chrissearle.flickrvote.service.model.impl.ImageItemInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("frontPageService")
@Transactional
public class DaoWinnerService implements WinnerService {
    private ImageDao imageDao;
    private ChallengeMessageService challengeMessageService;

    @Autowired
    public DaoWinnerService(ImageDao imageDao, ChallengeMessageService challengeMessageService) {
        this.imageDao = imageDao;
        this.challengeMessageService = challengeMessageService;
    }

    /**
     * Method getGoldWinners returns all images with first place.
     *
     * @return list of images with rank 1.
     */
    public Set<ImageItem> getGoldWinners() {
        Set<ImageItem> images = new HashSet<ImageItem>();

        for (Image image : imageDao.getImagesWithRank(1L)) {
            images.add(new ImageItemInstance(image));
        }

        return images;
    }

    public String getFrontPageHtml() {
        List<ImageItem> images = new ArrayList<ImageItem>(getGoldWinners());

        Collections.sort(images, new Comparator<ImageItem>() {
            public int compare(ImageItem o1, ImageItem o2) {
                return o2.getChallenge().getTag().compareTo(o1.getChallenge().getTag());
            }
        });

        return challengeMessageService.generateFrontPageHtml(images);
    }
}
