package exception;

public class HaveNotEnoughMoneyException extends RuntimeException {

    public HaveNotEnoughMoneyException(String message) {
        super(message);
    }

    public HaveNotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}
