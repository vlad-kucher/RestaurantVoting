package vlad.kucher.rv.web;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vlad.kucher.rv.util.exception.ErrorInfo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, ConstraintViolationException e) {
        return new ErrorInfo(req.getRequestURL().toString(), e);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorInfo illegal(HttpServletRequest req, IllegalArgumentException e) {
        return new ErrorInfo(req.getRequestURL().toString(), e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return new ErrorInfo(req.getRequestURL().toString(), e);
    }
}
