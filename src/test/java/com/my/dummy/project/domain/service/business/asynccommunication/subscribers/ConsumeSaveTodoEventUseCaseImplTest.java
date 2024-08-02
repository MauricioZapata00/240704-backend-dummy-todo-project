package com.my.dummy.project.domain.service.business.asynccommunication.subscribers;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class ConsumeSaveTodoEventUseCaseImplTest {

    @InjectMocks
    private ConsumeSaveTodoEventUseCaseImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLogTodoInformation(){
        Todo payloadForTest = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("title consumed by test")
                .build();

        underTest.process(payloadForTest).subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();
    }
}
