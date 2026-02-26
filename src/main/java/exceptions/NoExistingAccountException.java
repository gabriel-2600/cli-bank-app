package exceptions;

public class NoExistingAccountException extends RuntimeException {

    public NoExistingAccountException() {
        super();
    }

    public NoExistingAccountException(String message) {
        super(message);
    }

    public NoExistingAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
