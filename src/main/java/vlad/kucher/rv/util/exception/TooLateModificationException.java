package vlad.kucher.rv.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason = "Vote can't be changed after 11:00")  // 409
public class TooLateModificationException extends RuntimeException{
    public TooLateModificationException(String message) {
        super(message);
    }
}
