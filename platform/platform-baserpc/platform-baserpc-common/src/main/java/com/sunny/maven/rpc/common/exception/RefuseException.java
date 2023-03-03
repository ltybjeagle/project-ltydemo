package com.sunny.maven.rpc.common.exception;

/**
 * @author SUNNY
 * @description: RefuseException
 * @create: 2023-03-02 16:46
 */
public class RefuseException extends RuntimeException {
    private static final long serialVersionUID = 7525502600163466973L;

    /**
     * Instantiates a new Registry exception.
     * @param e the e
     */
    public RefuseException(final Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new Registry exception.
     * @param message the message
     */
    public RefuseException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Registry exception.
     * @param message the message
     * @param throwable the throwable
     */
    public RefuseException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
