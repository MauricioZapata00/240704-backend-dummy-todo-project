package com.my.dummy.project.application.useCase.business;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;

@FunctionalInterface
public interface ValidateTodoUseCase {
    Uni<Boolean> process(Todo todo);
}
