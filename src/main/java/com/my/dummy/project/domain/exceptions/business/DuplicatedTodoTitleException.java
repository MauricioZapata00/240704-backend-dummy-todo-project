package com.my.dummy.project.domain.exceptions.business;

import lombok.Getter;

@Getter
public class DuplicatedTodoTitleException extends RuntimeException {

    public enum DuplicatedTodoTitleType {
        TITLE_ALREADY_EXISTS("Todo title already exists");

        private final String message;
        DuplicatedTodoTitleType(String message) {
            this.message = message;
        }
        public DuplicatedTodoTitleException build(Throwable throwable) {
            return new DuplicatedTodoTitleException(this, throwable);
        }
    }

    private final DuplicatedTodoTitleType type;

    private DuplicatedTodoTitleException(DuplicatedTodoTitleType duplicatedTodoTitleType, Throwable cause) {
        super(duplicatedTodoTitleType.message, cause);
        this.type = duplicatedTodoTitleType;
    }
}
