package com.my.dummy.project.infrastructure.asynccommunication.subscribers.business;

import com.my.dummy.project.application.useCase.asynccommunication.subscribers.business.ConsumeSaveTodoEventUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class SaveTodoEventConsumer {

    private final ConsumeSaveTodoEventUseCase consumeSaveTodoEventUseCase;

    private static final String SAVED_TODO_MESSAGE_KEY = "todo.saved";

    @ConsumeEvent(SAVED_TODO_MESSAGE_KEY)
    public Uni<Todo> consumeSaveTodoEvent(Todo savedTodoReceived){
        return this.consumeSaveTodoEventUseCase.process(savedTodoReceived)
                .map(unused -> savedTodoReceived);
    }
}
