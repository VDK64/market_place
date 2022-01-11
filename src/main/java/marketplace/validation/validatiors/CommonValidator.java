package marketplace.validation.validatiors;

import marketplace.dto.web.BidValidationDto;
import marketplace.exception.BidException;
import marketplace.exception.ItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static marketplace.constants.ApplicationConstants.DATE_FORMAT_WITHOUT_TIME;
import static marketplace.exception.ItemErrorMessageConstants.WRONG_IMAGE_TYPE;

/**
 * Validator which consider main validation methods.
 */
@Component
public class CommonValidator {
    private static final List<String> CONTENT_TYPES;
    private static final SimpleDateFormat DATE_PARSER_WITHOUT_TIME;
    private static final String[] IMAGE_TYPES = {"jpeg", "png", "jpg"};

    @Autowired
    private Validator validator;

    static {
        DATE_PARSER_WITHOUT_TIME = new SimpleDateFormat(DATE_FORMAT_WITHOUT_TIME);
        CONTENT_TYPES = new ArrayList<>(Arrays.asList(IMAGE_TYPES));
    }

    /**
     * Validating method for validate parameters before write new bid in database.
     *
     * @param validationDto is a validation data transfer object.
     */
    public void validateBeforeBid(BidValidationDto validationDto) {
        final BeanPropertyBindingResult result = getBindingResult(validationDto);
        if (result.hasErrors()) {
            throw new BidException(result.getFieldErrors());
        }
    }

    private BeanPropertyBindingResult getBindingResult(BidValidationDto validationDto) {
        final BeanPropertyBindingResult result
                = new BeanPropertyBindingResult(validationDto, BidValidationDto.class.getName());
        validator.validate(validationDto, result);
        return result;
    }

    /**
     * Validates string representing stop time.
     *
     * @param timeString is a string which need to validate.
     */
    public boolean validateTime(String timeString) {
        try {
            LocalTime.parse(timeString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates string representing stop date.
     *
     * @param dateString is a string which need to validate.
     */
    public boolean validateDate(String dateString) {
        try {
            DATE_PARSER_WITHOUT_TIME.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates float value.
     *
     * @param value is a string which need to validate.
     * @throws NumberFormatException would be thrown if exception occur.
     */
    public boolean validateFloat(String value) throws NumberFormatException {
        try {
            Float.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates value on long type.
     *
     * @param value is a validation purpose.
     * @throws NumberFormatException would be thrown if exception occur.
     */
    public boolean validateLong(String value) throws NumberFormatException {
        try {
            Long.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Validates uploading file
     *
     * @param file which need to validate.
     */
    public void validateImage(MultipartFile file) {
        if (file != null && file.getSize() != 0) {
            final String contentType =
                    Objects.requireNonNull(file.getContentType()).split("/")[1];
            if (contentType != null && !CONTENT_TYPES.contains(contentType.toLowerCase())) {
                throw new ItemException(WRONG_IMAGE_TYPE);
            }
        }
    }

}
