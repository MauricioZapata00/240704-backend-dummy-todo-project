package com.my.dummy.project.domain.service.business;

import com.my.dummy.project.application.ports.nosql.mongo.business.TodoRepository;
import com.my.dummy.project.application.useCase.business.GetAllTodosUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Multi;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;


@ApplicationScoped
@RequiredArgsConstructor
public class GetAllTodosUseCaseImpl implements GetAllTodosUseCase {

    private final TodoRepository todoRepository;

    @Override
    public Multi<Todo> process() {
        return this.todoRepository.findAllTodos();
    }
}
