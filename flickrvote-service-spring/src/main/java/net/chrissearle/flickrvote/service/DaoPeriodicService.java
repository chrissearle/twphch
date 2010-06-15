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

import net.chrissearle.flickrvote.dao.PhotographyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service("periodicService")
@Transactional
public class DaoPeriodicService implements PeriodicService {
    private Logger logger = Logger.getLogger(DaoPeriodicService.class.getName());

    private PhotographyDao photographyDao;

    @Autowired
    public DaoPeriodicService(PhotographyDao photographyDao) {
        this.photographyDao = photographyDao;
    }

    @Override
    public void clearOldVotes() {
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Clearing old votes");
        }
        
        photographyDao.clearVotes();
    }
}
