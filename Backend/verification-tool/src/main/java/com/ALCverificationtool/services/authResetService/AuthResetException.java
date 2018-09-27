package com.ALCverificationtool.services.authResetService;

import com.ALCverificationtool.services.ServiceException;

public class AuthResetException extends ServiceException {
    public AuthResetException() {
    }

    public AuthResetException(String message) {
        super(message);
    }

    public AuthResetException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthResetException(Throwable cause) {
        super(cause);
    }

    public AuthResetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
