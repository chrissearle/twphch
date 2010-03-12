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

package net.chrissearle.flickrvote.dao;

import net.chrissearle.common.jpa.Dao;
import net.chrissearle.flickrvote.model.Photographer;

import java.util.List;

/**
 * Interface PhotographyDao provides access to the data layer for photographer objects.
 *
 * @author chris
 */
public interface PhotographyDao extends Dao<String, Photographer> {
    /**
     * Method findByUsername finds the photographer with the given username. Null if none found.
     *
     * @param username of type String
     * @return Photographer
     */
    Photographer findByUsername(String username);

    /**
     * Method findByToken finds the photographer with the given token. Null if none found.
     *
     * @param token of type String
     * @return Photographer
     */
    Photographer findByToken(String token);

    /**
     * Method clearVotes removes all votes from the entire system.
     */
    void clearVotes();

    /**
     * Method all returns all photographers
     *
     * @return List<Photographer>
     */
    List<Photographer> all();

    /**
     * Get all photographers that are admins
     *
     * @return List<Photographer>
     */
    List<Photographer> getAdmins();
}