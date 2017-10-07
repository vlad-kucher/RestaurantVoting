package vlad.kucher.rv.web;

import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vlad.kucher.rv.util.ValidationUtil;
import vlad.kucher.rv.util.exception.ErrorInfo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger log = getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, ConstraintViolationException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorInfo illegal(HttpServletRequest req, IllegalArgumentException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException){
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error("Error at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("Warning at request  {}: {}", req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL().toString(), rootCause);
    }
}
