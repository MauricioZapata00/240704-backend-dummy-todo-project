package com.my.dummy.project.domain.service.business;


import com.my.dummy.project.application.ports.nosql.mongo.business.TodoRepository;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Multi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class ValidateTodoUseCaseImplTest {

    @Mock
    private TodoRepository todoRepositoryMock;

    @InjectMocks
    private ValidateTodoUseCaseImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processValidateTodoUseCaseImplShouldReturnsFalse(){
        Todo firstTodoMockedForTest  = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("First Todo Test")
                .build();

        Todo secondTodoMockedForTest  = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("Second Todo Test")
                .build();

        Todo todoForUnderTest = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("Second Todo Test")
                .build();

        Mockito.when(todoRepositoryMock.findAllTodos())
                .thenReturn(Multi.createFrom().items(firstTodoMockedForTest, secondTodoMockedForTest));

        underTest.process(todoForUnderTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertFalse(underTestResult);
                });
    }

    @Test
    void processValidateTodoUseCaseImplShouldReturnsTrue(){
        Todo firstTodoMockedForTest  = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("Third Todo Test")
                .build();

        Todo secondTodoMockedForTest  = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("Fourth Todo Test")
                .build();

        Todo todoForUnderTest = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("A Todo Test")
                .build();

        Mockito.when(todoRepositoryMock.findAllTodos())
                .thenReturn(Multi.createFrom().items(firstTodoMockedForTest, secondTodoMockedForTest));

        underTest.process(todoForUnderTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertTrue(underTestResult);
                });
    }


}