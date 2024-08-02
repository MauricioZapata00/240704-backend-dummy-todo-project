package com.my.dummy.project.infrastructure.asynccommunication.subscribers.business;

import com.my.dummy.project.application.useCase.asynccommunication.subscribers.business.ConsumeSaveTodoEventUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SaveTodoEventConsumerTest {

    @Mock
    private ConsumeSaveTodoEventUseCase consumeSaveTodoEventUseCaseMock;

    @InjectMocks
    private SaveTodoEventConsumer underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallConsumeSaveTodoEventUseCase() {
        Mockito.when(consumeSaveTodoEventUseCaseMock.process(Mockito.any(Todo.class)))
                .thenReturn(Uni.createFrom().voidItem());
        Todo payloadForUnderTest = Todo.builder().build();

        var underTestResult = underTest.consumeSaveTodoEvent(payloadForUnderTest)
                .await().indefinitely();

        Assertions.assertNotNull(underTestResult);
        Assertions.assertEquals(payloadForUnderTest, underTestResult);
        Mockito.verify(consumeSaveTodoEventUseCaseMock, Mockito.times(1))
                .process(payloadForUnderTest);
    }
}
