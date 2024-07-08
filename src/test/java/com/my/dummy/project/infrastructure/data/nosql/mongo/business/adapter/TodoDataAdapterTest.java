package com.my.dummy.project.infrastructure.data.nosql.mongo.business.adapter;

import com.my.dummy.project.domain.model.business.Todo;
import com.my.dummy.project.infrastructure.data.nosql.mongo.business.document.TodoDocument;
import com.my.dummy.project.infrastructure.data.nosql.mongo.business.repository.TodoDataRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class TodoDataAdapterTest {

    @Mock
    private TodoDataRepository todoDataRepositoryMock;

    @InjectMocks
    private TodoDataAdapter underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnsAllTodos(){
        TodoDocument firstMockedTodoDocument = new TodoDocument();
        firstMockedTodoDocument.setId(UUID.randomUUID().toString());
        firstMockedTodoDocument.setTitle("First Todo");

        Mockito.when(todoDataRepositoryMock.streamAll())
                .thenReturn(Multi.createFrom().items(firstMockedTodoDocument));

        underTest.findAllTodos().subscribe()
                .with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(firstMockedTodoDocument.getId(), underTestResult.getId());
                    Assertions.assertEquals(firstMockedTodoDocument.getTitle(), underTestResult.getTitle());
                });

        Mockito.verify(todoDataRepositoryMock, Mockito.times(1))
                .streamAll();
    }

    @Test
    void shouldSaveTodoDocument(){
        Todo modelForTest = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title("Title test")
                .build();
        TodoDocument mockedTodoDocument = new TodoDocument();
        mockedTodoDocument.setId(modelForTest.getId());
        mockedTodoDocument.setTitle(modelForTest.getTitle());

        Mockito.when(todoDataRepositoryMock.persist(Mockito.any(TodoDocument.class)))
                .thenReturn(Uni.createFrom().item(mockedTodoDocument));

        underTest.saveTodo(modelForTest).subscribe()
                .with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(modelForTest.getId(), underTestResult.getId());
                    Assertions.assertEquals(modelForTest.getTitle(), underTestResult.getTitle());
                });

        Mockito.verify(todoDataRepositoryMock, Mockito.times(1))
                .persist(Mockito.any(TodoDocument.class));
    }
}
