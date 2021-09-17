package exception;

public class DBException extends RuntimeException {
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException(Throwable e) {
        super(e);
    }

    public DBException(String s) {
        super(s);
    }
}
