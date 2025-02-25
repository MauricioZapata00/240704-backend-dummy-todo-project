package com.my.dummy.project.infrastructure.cronJobsExecutions.business;

import com.my.dummy.project.application.useCase.business.GetAllTodosUseCase;
import com.my.dummy.project.domain.model.business.Todo;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.quarkus.scheduler.Trigger;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;



class TodoLoggerCronJobTest {

    @Mock
    private GetAllTodosUseCase getAllTodosUseCaseMock;

    @Mock
    private Scheduler schedulerMock;

    @Mock
    private Scheduler.JobDefinition jobDefinitionMock;

    @Mock
    private ScheduledExecution scheduledExecution;

    @Mock
    private Trigger triggerMock;

    @Captor
    private ArgumentCaptor<Function<ScheduledExecution, Uni<Void>>> taskCaptor;

    private List<Todo> todoListForTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(schedulerMock.newJob(Mockito.anyString()))
                .thenReturn(jobDefinitionMock);
        Mockito.when(jobDefinitionMock.setCron(Mockito.anyString()))
                .thenReturn(jobDefinitionMock);
        Mockito.when(jobDefinitionMock.setAsyncTask(
                        Mockito.<Function<ScheduledExecution, Uni<Void>>>any()))
                .thenReturn(jobDefinitionMock);
        Mockito.when(jobDefinitionMock.schedule())
                .thenReturn(triggerMock);
    }

    @Test
    void shouldScheduleCronJobSuccessfully() {
        todoListForTest = this.buildTodosListForTest();

        Mockito.when(getAllTodosUseCaseMock.process())
                .thenReturn(Multi.createFrom().iterable(todoListForTest));

        new TodoLoggerCronJob(getAllTodosUseCaseMock, schedulerMock);

        // Assert
        Mockito.verify(schedulerMock).newJob(Mockito.anyString());
        Mockito.verify(jobDefinitionMock).setCron("*/15 * * * * ?");
        Mockito.verify(jobDefinitionMock).setAsyncTask(taskCaptor.capture());
        Mockito.verify(jobDefinitionMock).schedule();

        // The captured task should not be null
        Function<ScheduledExecution, Uni<Void>> capturedTask = taskCaptor.getValue();
        Assertions.assertNotNull(capturedTask);
    }

    @Test
    void shouldScheduleCronJobSuccessfullyWithTask() {
        todoListForTest = this.buildTodosListForTest();

        Mockito.when(getAllTodosUseCaseMock.process())
                .thenReturn(Multi.createFrom().iterable(todoListForTest));

        new TodoLoggerCronJob(getAllTodosUseCaseMock, schedulerMock);

        Mockito.verify(schedulerMock).newJob(Mockito.anyString());
        Mockito.verify(jobDefinitionMock).setCron("*/15 * * * * ?");
        Mockito.verify(jobDefinitionMock).setAsyncTask(taskCaptor.capture());
        Mockito.verify(jobDefinitionMock).schedule();

        Function<ScheduledExecution, Uni<Void>> scheduleMethod = taskCaptor.getValue();

        var underTestResult = scheduleMethod.apply(scheduledExecution);

        Assertions.assertNotNull(underTestResult);

        underTestResult.subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertCompleted();

        Mockito.verify(getAllTodosUseCaseMock, Mockito.times(1))
                .process();

    }

    private List<Todo> buildTodosListForTest() {
        Todo todoNumber1 = Todo.builder()
                .id(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("Title 1 in test")
                .build();

        Todo todoNumber2 = Todo.builder()
                .id(UUID.randomUUID().toString().replaceAll("-", ""))
                .title("Title 2 in test")
                .build();

        List<Todo> mockedTodosForTest = new ArrayList<>();
        mockedTodosForTest.add(todoNumber1);
        mockedTodosForTest.add(todoNumber2);
        return mockedTodosForTest;
    }
}