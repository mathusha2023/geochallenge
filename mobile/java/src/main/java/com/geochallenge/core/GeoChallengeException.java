package com.geochallenge.core;

public class GeoChallengeException extends Exception {

    private final String code;

    public GeoChallengeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public GeoChallengeException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
