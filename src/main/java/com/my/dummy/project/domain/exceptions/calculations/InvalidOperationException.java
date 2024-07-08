package com.my.dummy.project.domain.exceptions.calculations;

import lombok.Getter;

@Getter
public class InvalidOperationException extends RuntimeException {

    public enum InvalidOperationType {
        DIVISION_BY_ZERO("Division by zero"),
        OPERATION_NOT_ALLOWED("Operation not allowed");

        private final String message;

        InvalidOperationType(String message) {
            this.message = message;
        }

        public InvalidOperationException build(Throwable throwable){
            return new InvalidOperationException(this, throwable);
        }
    }

    private final InvalidOperationType invalidOperationType;

    private InvalidOperationException(InvalidOperationType invalidOperationType, Throwable cause) {
        super(invalidOperationType.message, cause);
        this.invalidOperationType = invalidOperationType;
    }
}
