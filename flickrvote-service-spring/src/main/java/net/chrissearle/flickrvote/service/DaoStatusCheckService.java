package net.chrissearle.flickrvote.service;

import net.chrissearle.flickrvote.dao.ChallengeDao;
import net.chrissearle.flickrvote.flickr.FlickrStatusCheckService;
import net.chrissearle.flickrvote.flickr.model.FlickrImageStatus;
import net.chrissearle.flickrvote.model.Challenge;
import net.chrissearle.flickrvote.service.model.ImageItemStatus;
import net.chrissearle.flickrvote.service.model.impl.ImageItemStatusInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("statusCheckService")
@Transactional
public class DaoStatusCheckService implements StatusCheckService {
    private ChallengeDao challengeDao;
    private FlickrStatusCheckService flickrStatusCheckService;

    @Autowired
    public DaoStatusCheckService(ChallengeDao challengeDao, FlickrStatusCheckService flickrStatusCheckService) {
        this.challengeDao = challengeDao;
        this.flickrStatusCheckService = flickrStatusCheckService;
    }

    public Set<ImageItemStatus> checkSearch(String tag) {
        Challenge challenge = challengeDao.findByTag(tag);

        Set<ImageItemStatus> results = new HashSet<ImageItemStatus>();

        for (FlickrImageStatus status : flickrStatusCheckService.checkSearch(tag, challenge.getStartDate())) {
            results.add(new ImageItemStatusInstance(status));
        }

        return results;
    }
}
