package com.geochallenge.core;

import java.util.Optional;

/**
 * Discriminated result wrapper for native bridge operations.
 */
public final class Result<T> {

    private final T value;
    private final GeoChallengeException error;
    private final boolean success;

    private Result(T value, GeoChallengeException error, boolean success) {
        this.value = value;
        this.error = error;
        this.success = success;
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> Result<T> fail(GeoChallengeException error) {
        return new Result<>(null, error, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<GeoChallengeException> getError() {
        return Optional.ofNullable(error);
    }

    public T unwrap() throws GeoChallengeException {
        if (!success) {
            throw error;
        }
        return value;
    }
}
