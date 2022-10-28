package com.erecruitment.exceptions;

public class PermissionErrorException extends RuntimeException {
    public PermissionErrorException() {
        super();
    }

    public PermissionErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionErrorException(String message) {
        super(message);
    }

    public PermissionErrorException(Throwable cause) {
        super(cause);
    }
}
