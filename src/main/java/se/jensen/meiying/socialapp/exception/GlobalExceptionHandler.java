package se.jensen.meiying.socialapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * Global exception handler for the application.
 * <p>
 * Handles common exceptions thrown by controllers and services
 * and maps them to appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when an element is not found.
     *
     * @param ex the thrown NoSuchElementException
     * @return a ResponseEntity with HTTP status 404 (NOT_FOUND) and exception message
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles illegal argument exceptions.
     *
     * @param ex the thrown IllegalArgumentException
     * @return a ResponseEntity with HTTP status 400 (BAD_REQUEST) and exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles all other uncaught exceptions.
     *
     * @param ex the thrown Exception
     * @return a ResponseEntity with HTTP status 500 (INTERNAL_SERVER_ERROR)
     * and a generic error message including the exception message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ett oväntat fel inträffade: " + ex.getMessage());
    }
}
