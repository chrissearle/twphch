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

package net.chrissearle.flickrvote.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import net.chrissearle.flickrvote.service.ChallengeService;
import net.chrissearle.flickrvote.service.model.ChallengeSummary;
import net.chrissearle.flickrvote.web.FlickrVoteWebConstants;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class OverlapValidator extends FieldValidatorSupport {
    @Autowired
    private ChallengeService challengeService;

    private String keyName;

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Date fieldValue = (Date) getFieldValue(fieldName, object);

        Date startDateWithTime = new DateTime(fieldValue).plusHours(FlickrVoteWebConstants.START_CHALLENGE_TIME).toDate();

        List<ChallengeSummary> challenges = challengeService.isDateAvailable(startDateWithTime);

        if (challenges.size() > 1) {
            // Guaranteed crash
            addFieldError(fieldName, object);
        } else {
            if (challenges.size() == 1) {
                // Need to check - is it an update?
                String keyValue = (String) getFieldValue(keyName, object);

                if (!challenges.get(0).getTag().equals(keyValue)) {
                    addFieldError(fieldName, object);
                }

            }
        }
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}