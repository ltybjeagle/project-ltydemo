package com.sunny.maven.rpc.common.exception;

/**
 * @author SUNNY
 * @description: SerializerException
 * @create: 2022-12-28 12:35
 */
public class SerializerException extends RuntimeException {
    private static final long serialVersionUID = -8756503895961683400L;

    /**
     * Instantiates a new Serializer exception.
     * @param e the e
     */
    public SerializerException(final Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new Serializer exception.
     * @param message the message
     */
    public SerializerException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Serializer exception.
     * @param message the message
     * @param throwable the throwable
     */
    public SerializerException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
