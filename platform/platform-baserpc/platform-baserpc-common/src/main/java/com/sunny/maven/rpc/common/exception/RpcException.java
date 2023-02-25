package com.sunny.maven.rpc.common.exception;

/**
 * @author SUNNY
 * @description: RpcException
 * @create: 2023-02-25 13:27
 */
public class RpcException extends RuntimeException {
    private static final long serialVersionUID = -2389942870843087411L;

    /**
     * Instantiates a new Serializer exception.
     * @param e the e
     */
    public RpcException(final Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new Serializer exception.
     * @param message the message
     */
    public RpcException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Serializer exception.
     * @param message the message
     * @param throwable the throwable
     */
    public RpcException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
