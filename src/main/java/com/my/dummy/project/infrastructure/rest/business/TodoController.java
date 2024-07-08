package com.my.dummy.project.infrastructure.rest.business;

import com.my.dummy.project.application.useCase.business.GetAllTodosUseCase;
import com.my.dummy.project.application.useCase.business.SaveTodoUseCase;
import com.my.dummy.project.domain.exceptions.business.DuplicatedTodoTitleException;
import com.my.dummy.project.domain.model.business.Todo;
import com.my.dummy.project.infrastructure.rest.business.dto.TodoDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/api/v1/business/todos")
@RequestScoped
@RequiredArgsConstructor
public class TodoController {
    private final GetAllTodosUseCase getAllTodosUseCase;
    private final SaveTodoUseCase saveTodoUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Response> getAllTodos() {
        log.info("getting all todos");
        return this.getAllTodosUseCase.process()
                .map(this::mapModelToDTO)
                .map(todoDTO -> Response.ok(todoDTO).build())
                .onFailure().recoverWithItem(this::validateException);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> saveTodo(TodoDTO todoDTO) {
        log.info("saving todo {}", todoDTO);
        return this.saveTodoUseCase.process(this.mapDTOToModel(todoDTO))
                .map(this::mapModelToDTO)
                .map(todo -> Response.status(Response.Status.CREATED).entity(todo).build())
                .onFailure().recoverWithItem(this::validateException);
    }

    private TodoDTO mapModelToDTO(Todo todo) {
        TodoDTO todoDTOCreated = new TodoDTO();
        todoDTOCreated.setId(todo.getId());
        todoDTOCreated.setTitle(todo.getTitle());
        return todoDTOCreated;
    }

    private Todo mapDTOToModel(TodoDTO todoDTO) {
        return Todo.builder()
                .id(todoDTO.getId())
                .title(todoDTO.getTitle())
                .build();
    }

    private Response validateException(Throwable throwable) {
        log.error(throwable.getMessage());
        Response.Status statusResponse = Response.Status.INTERNAL_SERVER_ERROR;
        if (DuplicatedTodoTitleException.class.equals(throwable.getClass())){
            statusResponse = Response.Status.BAD_REQUEST;
        }

        return Response.status(statusResponse)
                .entity(throwable.getMessage())
                .build();
    }
}
