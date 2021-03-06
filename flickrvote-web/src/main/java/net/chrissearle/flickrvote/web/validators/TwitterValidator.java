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
import net.chrissearle.spring.twitter.service.UserExistanceService;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterValidator extends FieldValidatorSupport {
    @Autowired
    private UserExistanceService userExistanceService;

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        String fieldValue = (String) getFieldValue(fieldName, object);

        if (fieldValue.startsWith("@")) {
            fieldValue = fieldValue.substring(1);
        }

        if (!userExistanceService.checkIfUserExists(fieldValue)) {
            addFieldError(fieldName, object);
        }
    }
}