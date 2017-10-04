package vlad.kucher.rv.util.exception;

public class TooLateModificationException extends RuntimeException{
    public TooLateModificationException(String message) {
        super(message);
    }
}
