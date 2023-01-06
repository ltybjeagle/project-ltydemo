package com.sunny.maven.rpc.common.exception;

/**
 * @author SUNNY
 * @description: RegistryException
 * @create: 2023-01-05 15:26
 */
public class RegistryException extends RuntimeException {
    private static final long serialVersionUID = 2537584305509425007L;

    /**
     * Instantiates a new Registry exception.
     * @param e the e
     */
    public RegistryException(final Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new Registry exception.
     * @param message the message
     */
    public RegistryException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Registry exception.
     * @param message the message
     * @param throwable the throwable
     */
    public RegistryException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
