package com.my.dummy.project.infrastructure.asynccommunication.publishers.business;

import com.my.dummy.project.application.useCase.asynccommunication.publishers.business.PublishSavedTodoEventUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class SaveTodoEventPublisher implements PublishSavedTodoEventUseCase {

    private final EventBus eventBus;

    private static final String SAVED_TODO_MESSAGE_KEY = "todo.saved";
    @Override
    public Uni<Todo> process(Todo todoSaved) {
        return this.eventBus.<Todo>request(SAVED_TODO_MESSAGE_KEY, todoSaved)
                .map(Message::body);
    }
}
