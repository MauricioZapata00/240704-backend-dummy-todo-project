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


class GetAllTodosUseCaseImplTest {

    @Mock
    private TodoRepository todoRepositoryMock;

    @InjectMocks
    private GetAllTodosUseCaseImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllTodosUseCase() {
        Todo expectedTodo = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("todo title test")
                .build();
        Mockito.when(todoRepositoryMock.findAllTodos())
                .thenReturn(Multi.createFrom().items(expectedTodo));

        underTest.process().subscribe()
                .with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(expectedTodo, underTestResult);
                });

        Mockito.verify(todoRepositoryMock, Mockito.times(1))
                .findAllTodos();
    }
}
