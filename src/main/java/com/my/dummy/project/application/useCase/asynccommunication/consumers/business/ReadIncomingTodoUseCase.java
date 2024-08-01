package com.my.dummy.project.application.useCase.asynccommunication.consumers.business;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;

@FunctionalInterface
public interface ReadIncomingTodoUseCase {
    Uni<Void> process(Todo todoMessage);
}
