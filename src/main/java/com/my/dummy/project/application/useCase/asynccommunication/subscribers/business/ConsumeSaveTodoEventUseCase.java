package com.my.dummy.project.application.useCase.asynccommunication.subscribers.business;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;

@FunctionalInterface
public interface ConsumeSaveTodoEventUseCase {
    Uni<Void> process(Todo todoSaved);
}
