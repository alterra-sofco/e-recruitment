package com.erecruitment.exceptions;

public class CredentialErrorException extends RuntimeException {

    public CredentialErrorException() {
        super();
    }

    public CredentialErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialErrorException(String message) {
        super(message);
    }

    public CredentialErrorException(Throwable cause) {
        super(cause);
    }
}
