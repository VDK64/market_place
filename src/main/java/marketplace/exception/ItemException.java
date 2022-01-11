package marketplace.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Extended from <code>RuntimeException</code> class to implement
 * exceptions related to items.
 */
@Getter
public class ItemException extends RuntimeException {
    private List<FieldError> errors;

    public ItemException(String message) {
        super(message);
    }

    public ItemException(List<FieldError> errors) {
        this.errors = errors;
    }

    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }

}
