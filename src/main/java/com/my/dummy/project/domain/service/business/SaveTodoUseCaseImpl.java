package com.my.dummy.project.domain.service.business;

import com.my.dummy.project.application.ports.nosql.mongo.business.TodoRepository;
import com.my.dummy.project.application.useCase.business.SaveTodoUseCase;
import com.my.dummy.project.application.useCase.business.ValidateTodoUseCase;
import com.my.dummy.project.domain.exceptions.business.DuplicatedTodoTitleException;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class SaveTodoUseCaseImpl implements SaveTodoUseCase {

    private final TodoRepository todoRepository;

    private final ValidateTodoUseCase validateTodoUseCase;

    @Override
    public Uni<Todo> process(Todo todoToSave) {
        return this.validateTodoUseCase.process(todoToSave)
                .map(isTodoValidated -> this.checkTodoToStore(isTodoValidated, todoToSave))
                .flatMap(this.todoRepository::saveTodo)
                .onFailure(DuplicatedTodoTitleException.class).invoke(throwable -> log.error(throwable.getMessage()));
    }

    private Todo checkTodoToStore(Boolean isTodoValidated, Todo todoToCheck) {
        if (Boolean.FALSE.equals(isTodoValidated)){
            throw DuplicatedTodoTitleException
                    .DuplicatedTodoTitleType
                    .TITLE_ALREADY_EXISTS
                    .build(new Throwable("There is at least one todo with the same title."));
        }
        return todoToCheck;
    }
}
