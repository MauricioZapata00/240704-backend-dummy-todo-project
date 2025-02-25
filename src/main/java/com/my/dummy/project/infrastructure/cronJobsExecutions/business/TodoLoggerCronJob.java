package com.my.dummy.project.infrastructure.cronJobsExecutions.business;


import com.my.dummy.project.application.useCase.business.GetAllTodosUseCase;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.ScheduledExecution;
import io.quarkus.scheduler.Scheduler;
import io.quarkus.scheduler.Trigger;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@Startup
public class TodoLoggerCronJob {

    private final GetAllTodosUseCase getAllTodosUseCase;
    private final Scheduler customScheduler;

    public TodoLoggerCronJob(GetAllTodosUseCase getAllTodosUseCase, Scheduler scheduler) {
        this.getAllTodosUseCase = getAllTodosUseCase;
        this.customScheduler = scheduler;
        Trigger trigger = this.customScheduler.newJob(UUID.randomUUID().toString().replace("-", ""))
                .setCron("*/15 * * * * ?")
                .setAsyncTask(scheduleCustomCronJob())
                .schedule();
        log.info("Scheduled job with ID: {}", trigger.getId());
    }

    private Function<ScheduledExecution, Uni<Void>> scheduleCustomCronJob() {
        return scheduledExecution -> this.getAllTodosUseCase.process()
                .collect().asList()
                .invoke(todo -> log.info("Todo read is: {}", todo.toString()))
                .replaceWithVoid();
    }
}
