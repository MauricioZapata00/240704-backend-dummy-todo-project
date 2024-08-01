package com.my.dummy.project.infrastructure.asynccommunication.rabbitmq.consumers.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dummy.project.application.useCase.asynccommunication.consumers.business.ReadIncomingTodoUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import com.my.dummy.project.infrastructure.asynccommunication.rabbitmq.consumers.business.dto.TodoMessageQueue;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class TodoConsumer {

    private final ReadIncomingTodoUseCase readIncomingTodoUseCase;

    private final ObjectMapper objectMapper;


    @SneakyThrows
    @Incoming("todos.queue")
    public Uni<Void> process(Message<byte[]> message) {
        TodoMessageQueue todoMessageQueue = this.mapPayloadToMessage(message.getPayload());
        log.info("message received: {}", todoMessageQueue.toString());
        return this.readIncomingTodoUseCase
                .process(this.mapMessageToModel(todoMessageQueue));
    }

    private TodoMessageQueue mapPayloadToMessage(byte[] message) throws IOException {
        try{
            return this.objectMapper.readValue(message, TodoMessageQueue.class);
        }
        catch (IOException e){
            throw new IOException(e.getMessage());
        }
    }

    private Todo mapMessageToModel(TodoMessageQueue todoMessageQueue) {
        return Todo.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .title(todoMessageQueue.getTitle())
                .build();
    }
}
