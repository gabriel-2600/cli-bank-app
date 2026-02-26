package exceptions;

public class AmountLessThanZeroException extends RuntimeException {

    public AmountLessThanZeroException() {
        super();
    }

    public AmountLessThanZeroException(String message) {
        super(message);
    }

    public AmountLessThanZeroException(String message, Throwable cause) {
        super(message, cause);
    }
}
