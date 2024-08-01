package com.my.dummy.project.infrastructure.asynccommunication.rabbitmq.consumers.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.dummy.project.application.useCase.asynccommunication.consumers.business.ReadIncomingTodoUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import com.my.dummy.project.infrastructure.asynccommunication.rabbitmq.consumers.business.dto.TodoMessageQueue;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

class TodoConsumerTest {

    @Mock
    private ReadIncomingTodoUseCase readIncomingTodoUseCaseMock;

    @Mock
    private ObjectMapper objectMapperMock;

    @InjectMocks
    private TodoConsumer underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReceiveMessageTodo() throws IOException {
        TodoMessageQueue mockedTodoMessageQueue = new TodoMessageQueue("title for test");
        byte[] mockPayload = "mock payload".getBytes();
        Message<byte[]> mockedMessage = Message.of(mockPayload);
        Mockito.when(objectMapperMock.readValue(mockPayload, TodoMessageQueue.class))
                        .thenReturn(mockedTodoMessageQueue);
        Mockito.when(readIncomingTodoUseCaseMock.process(Mockito.any(Todo.class)))
                .thenReturn(Uni.createFrom().voidItem());
        var underTestResult = underTest.process(mockedMessage)
                .await().indefinitely();

        Assertions.assertNull(underTestResult);

        Mockito.verify(objectMapperMock, Mockito.times(1))
                .readValue(mockPayload, TodoMessageQueue.class);
        Mockito.verify(readIncomingTodoUseCaseMock, Mockito.times(1))
                .process(Mockito.any(Todo.class));
    }

    @Test
    void shouldHandleDeserializationException() throws IOException {
        // Arrange
        byte[] mockPayload = "invalid payload".getBytes();
        Message<byte[]> mockedMessage = Message.of(mockPayload);
        JsonProcessingException errorForTest = new JsonProcessingException("Deserialization error mocked in test"){};

        Mockito.when(objectMapperMock.readValue(mockPayload, TodoMessageQueue.class))
                .thenThrow(errorForTest);

        try {
            // Act
            underTest.process(mockedMessage)
                    .subscribe().withSubscriber(UniAssertSubscriber.create())
                    .assertFailedWith(JsonProcessingException.class, errorForTest.getMessage());
        }catch (Exception underTestResult){
            // Assert
            Assertions.assertNotNull(underTestResult);
            Assertions.assertEquals(errorForTest.getMessage(), underTestResult.getMessage());
            Mockito.verify(readIncomingTodoUseCaseMock, Mockito.never())
                    .process(Mockito.any(Todo.class));
        }


    }
}
