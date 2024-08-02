package com.my.dummy.project.domain.service.business.asynccommunication.subscribers;

import com.my.dummy.project.application.useCase.asynccommunication.subscribers.business.ConsumeSaveTodoEventUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ConsumeSaveTodoEventUseCaseImpl implements ConsumeSaveTodoEventUseCase {
    @Override
    public Uni<Void> process(Todo todoSaved) {
        return Uni.createFrom().item(todoSaved)
                .onItem().invoke(todo -> log.info("Todo saved and received from vertx.mutini core: " +
                        "id:{}, title:{}", todo.getId(), todo.getTitle()))
                .replaceWithVoid();
    }
}
