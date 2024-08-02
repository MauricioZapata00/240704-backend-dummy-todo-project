package com.my.dummy.project.application.useCase.asynccommunication.publishers.business;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;

@FunctionalInterface
public interface PublishSavedTodoEventUseCase {
    Uni<Todo> process(Todo todoSaved);
}
