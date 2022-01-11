package marketplace.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Extended from <code>RuntimeException</code> class to implement
 * exceptions related to bid.
 */
@Getter
public class BidException extends RuntimeException {
    private List<FieldError> errors;

    public BidException(String message) {
        super(message);
    }

    public BidException(List<FieldError> errors) {
        this.errors = errors;
    }

    public BidException(String message, Throwable cause) {
        super(message, cause);
    }

}
