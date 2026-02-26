package exceptions;

public class SameBankAccountException extends RuntimeException {

    public SameBankAccountException() {
        super();
    }

    public SameBankAccountException(String message) {
        super(message);
    }

    public SameBankAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
