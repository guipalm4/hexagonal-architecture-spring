package com.guipalm4.domain.validation;

public record Error(String message) {
    public static Error with(String message) {
        return new Error(message);
    }
}
