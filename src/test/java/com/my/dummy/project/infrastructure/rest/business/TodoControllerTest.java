package com.my.dummy.project.infrastructure.rest.business;

import com.my.dummy.project.application.useCase.business.GetAllTodosUseCase;
import com.my.dummy.project.application.useCase.business.SaveTodoUseCase;
import com.my.dummy.project.domain.exceptions.business.DuplicatedTodoTitleException;
import com.my.dummy.project.domain.model.business.Todo;
import com.my.dummy.project.infrastructure.rest.business.dto.TodoDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class TodoControllerTest {

    @Mock
    private GetAllTodosUseCase getAllTodosUseCaseMock;

    @Mock
    private SaveTodoUseCase saveTodoUseCaseMock;

    @InjectMocks
    private TodoController underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTodosShouldReturnsInternalServerError() {
        String errorString = "An error occurred while retrieving all todos in test";
        Response expectedResponse = Response.status(500)
                .entity(errorString)
                .build();
        Mockito.when(getAllTodosUseCaseMock.process())
                .thenReturn(Multi.createFrom().failure(new Throwable(errorString)));

        underTest.getAllTodos()
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                    Assertions.assertEquals(expectedResponse.getEntity(), underTestResponse.getEntity());
                });

        Mockito.verify(getAllTodosUseCaseMock, Mockito.times(1))
                .process();
    }

    @Test
    void getAllTodosShouldReturnsTodos() {
        String errorString = "An error occurred while retrieving all todos in test";
        TodoDTO expectedTodoDTO = new TodoDTO();
        expectedTodoDTO.setId(UUID.randomUUID().toString());
        expectedTodoDTO.setTitle("Title for test");
        Todo mockedTodo = Todo.builder()
                .id(expectedTodoDTO.getId())
                .title(expectedTodoDTO.getTitle())
                .build();
        Response expectedResponse = Response.status(200)
                .entity(errorString)
                .build();

        Mockito.when(getAllTodosUseCaseMock.process())
                .thenReturn(Multi.createFrom().items(mockedTodo));

        underTest.getAllTodos()
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                });

        Mockito.verify(getAllTodosUseCaseMock, Mockito.times(1))
                .process();
    }

    @Test
    void saveTodoShouldReturnsInternalServerError() {
        String errorString = "An error occurred while retrieving all todos in test";
        Response expectedResponse = Response.status(500)
                .entity(errorString)
                .build();
        TodoDTO expectedTodoDTO = new TodoDTO();
        expectedTodoDTO.setId(UUID.randomUUID().toString());
        expectedTodoDTO.setTitle("Title for test");
        Mockito.when(saveTodoUseCaseMock.process(Mockito.any(Todo.class)))
                .thenReturn(Uni.createFrom().failure(new Throwable(errorString)));

        underTest.saveTodo(expectedTodoDTO)
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                    Assertions.assertEquals(expectedResponse.getEntity(), underTestResponse.getEntity());
                });

        Mockito.verify(saveTodoUseCaseMock, Mockito.times(1))
                .process(Mockito.any(Todo.class));
    }

    @Test
    void saveTodoShouldReturnsBadRequest() {
        String errorString = "An error occurred while retrieving all todos in test";
        Response expectedResponse = Response.status(400)
                .entity(errorString)
                .build();
        TodoDTO expectedTodoDTO = new TodoDTO();
        expectedTodoDTO.setId(UUID.randomUUID().toString());
        expectedTodoDTO.setTitle("Title for test");
        Mockito.when(saveTodoUseCaseMock.process(Mockito.any(Todo.class)))
                .thenReturn(Uni.createFrom().failure(DuplicatedTodoTitleException.DuplicatedTodoTitleType
                        .TITLE_ALREADY_EXISTS.build(new Throwable(errorString))));

        underTest.saveTodo(expectedTodoDTO)
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                });

        Mockito.verify(saveTodoUseCaseMock, Mockito.times(1))
                .process(Mockito.any(Todo.class));
    }

    @Test
    void saveTodoShouldReturnsCreatedTodo() {
        TodoDTO expectedTodoDTO = new TodoDTO();
        expectedTodoDTO.setId(UUID.randomUUID().toString());
        expectedTodoDTO.setTitle("Title for test");
        Response expectedResponse = Response.status(201)
                .entity(expectedTodoDTO)
                .build();
        Todo mockedTodo = Todo.builder()
                .id(expectedTodoDTO.getId())
                .title(expectedTodoDTO.getTitle())
                .build();
        Mockito.when(saveTodoUseCaseMock.process(Mockito.any(Todo.class)))
                .thenReturn(Uni.createFrom().item(mockedTodo));

        underTest.saveTodo(expectedTodoDTO)
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                });

        Mockito.verify(saveTodoUseCaseMock, Mockito.times(1))
                .process(Mockito.any(Todo.class));
    }
}
