package seedu.address.logic.commands.exceptions;

/**
 * Represents an unexpected type provided at runtime.
 */
public class UnexpectedTypeException extends Exception {
    public UnexpectedTypeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UnexpectedTypeException} with the specified detail {@code message} and {@code cause}.
     */
    public UnexpectedTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
