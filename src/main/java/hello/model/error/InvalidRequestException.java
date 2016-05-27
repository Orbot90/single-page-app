package hello.model.error;

import org.springframework.validation.Errors;

/**
 * Created by orbot on 06.12.15.
 */
public class InvalidRequestException extends RuntimeException {
    private Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return this.errors;
    }
}
