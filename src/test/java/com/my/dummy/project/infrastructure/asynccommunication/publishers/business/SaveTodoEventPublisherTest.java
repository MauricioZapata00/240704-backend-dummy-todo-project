package com.my.dummy.project.infrastructure.asynccommunication.publishers.business;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class SaveTodoEventPublisherTest {

    @Mock
    private EventBus eventBusMock;

    @Mock
    private Message<Todo> mockedMessage;

    @InjectMocks
    private SaveTodoEventPublisher underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldPublishEvent() {
        Todo todoMockedForTest = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("title for test")
                .build();

        Mockito.when(mockedMessage.body()).thenReturn(todoMockedForTest);

        Mockito.when(eventBusMock.<Todo>request(Mockito.anyString(), Mockito.any(Todo.class)))
                .thenReturn(Uni.createFrom().item(mockedMessage));

        var underTestResult = underTest.process(todoMockedForTest)
                .await().indefinitely();

        Assertions.assertNotNull(underTestResult);
        Assertions.assertEquals(mockedMessage.body(), underTestResult);
        Mockito.verify(eventBusMock, Mockito.times(1))
                .<Todo>request(Mockito.anyString(), Mockito.any(Todo.class));
    }
}
