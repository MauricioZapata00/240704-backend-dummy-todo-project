package com.my.dummy.project.domain.service.business;

import com.my.dummy.project.application.ports.nosql.mongo.business.TodoRepository;
import com.my.dummy.project.application.useCase.business.ValidateTodoUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;


@ApplicationScoped
@RequiredArgsConstructor
public class ValidateTodoUseCaseImpl implements ValidateTodoUseCase {

    private final TodoRepository todoRepository;

    @Override
    public Uni<Boolean> process(Todo todo) {
        return this.todoRepository.findAllTodos()
                .filter(currentTodo -> currentTodo.getTitle().equals(todo.getTitle()))
                .onItem().transform(unusedTodo -> Boolean.FALSE).toUni()
                .replaceIfNullWith(Boolean.TRUE);
    }
}
