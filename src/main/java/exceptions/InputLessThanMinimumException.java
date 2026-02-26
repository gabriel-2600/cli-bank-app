package exceptions;

public class InputLessThanMinimumException extends RuntimeException {

    public InputLessThanMinimumException() {
        super();
    }

    public InputLessThanMinimumException(String message) {
        super(message);
    }

    public InputLessThanMinimumException(String message, Throwable cause) {
        super(message, cause);
    }
}
