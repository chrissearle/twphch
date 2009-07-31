package net.chrissearle.flickrvote.web.validators;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.Locale;

public class DayOfWeekValidator extends FieldValidatorSupport {
    private String dayOfWeek;

    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        Date fieldValue = (Date) getFieldValue(fieldName, object);

        DateTime date = new DateTime(fieldValue);

        if (!date.dayOfWeek().getAsShortText(Locale.ENGLISH).equalsIgnoreCase(dayOfWeek)) {
            addFieldError(fieldName, object);
        }
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
