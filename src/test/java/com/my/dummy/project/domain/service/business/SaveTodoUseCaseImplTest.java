package com.my.dummy.project.domain.service.business;

import com.my.dummy.project.application.ports.nosql.mongo.business.TodoRepository;
import com.my.dummy.project.application.useCase.business.ValidateTodoUseCase;
import com.my.dummy.project.domain.exceptions.business.DuplicatedTodoTitleException;
import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class SaveTodoUseCaseImplTest {

    @Mock
    private TodoRepository todoRepositoryMock;

    @Mock
    private ValidateTodoUseCase validateTodoUseCaseMock;

    @InjectMocks
    private SaveTodoUseCaseImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowsExceptionWhenSaveTodoUseCaseIsNotValid() {
        Todo todoForTest = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("todo title test")
                .build();
        DuplicatedTodoTitleException expectedException = DuplicatedTodoTitleException.DuplicatedTodoTitleType
                        .TITLE_ALREADY_EXISTS.build(new Throwable("Error in test"));
        Mockito.when(validateTodoUseCaseMock.process(todoForTest))
                        .thenReturn(Uni.createFrom().item(Boolean.FALSE));

        var underTestResult = underTest.process(todoForTest)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(DuplicatedTodoTitleException.class, expectedException.getMessage())
                .getFailure();

        Assertions.assertNotNull(underTestResult);
        Assertions.assertInstanceOf(DuplicatedTodoTitleException.class, underTestResult);
        Assertions.assertEquals(expectedException.getMessage(), underTestResult.getMessage());

        Mockito.verify(todoRepositoryMock, Mockito.never())
                .saveTodo(Mockito.any(Todo.class));
        Mockito.verify(validateTodoUseCaseMock, Mockito.times(1))
                .process(todoForTest);
    }

    @Test
    void shouldStoreTodoWhenSaveTodoUseCaseIsValid() {
        Todo todoForTest = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("todo title test")
                .build();
        Mockito.when(validateTodoUseCaseMock.process(todoForTest))
                .thenReturn(Uni.createFrom().item(Boolean.TRUE));
        Mockito.when(todoRepositoryMock.saveTodo(todoForTest))
                .thenReturn(Uni.createFrom().item(todoForTest));

        underTest.process(todoForTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(todoForTest, underTestResult);
                });



        Mockito.verify(todoRepositoryMock, Mockito.times(1))
                .saveTodo(todoForTest);
        Mockito.verify(validateTodoUseCaseMock, Mockito.times(1))
                .process(todoForTest);
    }
}
