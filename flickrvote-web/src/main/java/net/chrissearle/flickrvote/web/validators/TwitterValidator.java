package net.chrissearle.flickrvote.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import net.chrissearle.flickrvote.service.PhotographyService;
import org.springframework.beans.factory.annotation.Autowired;

public class TwitterValidator extends FieldValidatorSupport {
    @Autowired
    private PhotographyService photographyService;

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        String fieldValue = (String) getFieldValue(fieldName, object);

        if (fieldValue.startsWith("@")) {
            fieldValue = fieldValue.substring(1);
        }

        if (!photographyService.checkTwitterExists(fieldValue)) {
            addFieldError(fieldName, object);
        }
    }
}