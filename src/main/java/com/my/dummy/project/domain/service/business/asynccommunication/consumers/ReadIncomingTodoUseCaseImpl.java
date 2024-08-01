package com.my.dummy.project.domain.service.business.asynccommunication.consumers;

import com.my.dummy.project.application.useCase.asynccommunication.consumers.business.ReadIncomingTodoUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class ReadIncomingTodoUseCaseImpl implements ReadIncomingTodoUseCase {

    @Override
    public Uni<Void> process(Todo todoMessage) {
        return Uni.createFrom().item(todoMessage)
                .onItem().invoke(todo -> log.info("todo received: id: {}, title: {}, ",
                        todo.getId(),
                        todo.getTitle()))
                .replaceWithVoid();
    }
}
