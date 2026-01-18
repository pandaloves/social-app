package se.jensen.meiying.socialapp.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A simple application logger wrapper using SLF4J.
 * <p>
 * Provides methods for logging messages at different levels: INFO, WARN, and ERROR.
 * This class can be injected wherever logging is needed in the backend.
 */
@Component
public class AppLogger {

    private final Logger logger = LoggerFactory.getLogger(AppLogger.class);

    /**
     * Logs a message at INFO level.
     *
     * @param message the message to log
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * Logs a message at WARN level.
     *
     * @param message the message to log
     */
    public void warn(String message) {
        logger.warn(message);
    }

    /**
     * Logs a message at ERROR level.
     *
     * @param message the message to log
     */
    public void error(String message) {
        logger.error(message);
    }

    /**
     * Logs a message and a throwable (exception) at ERROR level.
     *
     * @param message   the message to log
     * @param throwable the exception or error to log
     */
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
