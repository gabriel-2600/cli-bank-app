package exceptions;

public class AmountLessThanBalanceException extends RuntimeException {

    public AmountLessThanBalanceException() {
        super();
    }

    public AmountLessThanBalanceException(String message) {
        super(message);
    }

    public AmountLessThanBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
