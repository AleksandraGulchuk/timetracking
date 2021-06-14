package com.epam.timetracking.exception;

/**
 * Exception class for handling errors that occur in services.
 */
public class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }
}
