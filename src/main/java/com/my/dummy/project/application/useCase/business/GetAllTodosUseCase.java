package com.my.dummy.project.application.useCase.business;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Multi;

@FunctionalInterface
public interface GetAllTodosUseCase {
    Multi<Todo> process();
}
