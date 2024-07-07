package de.robinmohr.backend;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


/**
 * This class is a controller advice used for handling validation errors in the application.
 * It provides exception handling methods for ConstraintViolationException and MethodArgumentNotValidException.
 */
@ControllerAdvice
public class ValidationErrorHandlingAdvice {

    /**
     * Handles ConstraintViolationException and returns a list of error messages.
     *
     * @param e The ConstraintViolationException to handle.
     *
     * @return A list of error messages generated from the ConstraintViolationException.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<String> onConstraintValidationException(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s",
                                                violation.getPropertyPath()
                                                         .toString(),
                                                violation.getMessage()))
                .toList();
    }

    /**
     * Handle MethodArgumentNotValidException and return a list of error messages.
     *
     * @param e The MethodArgumentNotValidException instance to be handled.
     *
     * @return A list of error messages for field validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
    }

}
