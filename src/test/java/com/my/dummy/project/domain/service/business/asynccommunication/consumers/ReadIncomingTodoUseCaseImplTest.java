package com.my.dummy.project.domain.service.business.asynccommunication.consumers;

import com.my.dummy.project.domain.model.business.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class ReadIncomingTodoUseCaseImplTest {

    @InjectMocks
    private ReadIncomingTodoUseCaseImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLogsTodoInformation(){
        Todo todoForTest = Todo.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .title("todo title test")
                .build();

        var underTestResult = underTest.process(todoForTest)
                .await().indefinitely();

        Assertions.assertNull(underTestResult);
    }
}
