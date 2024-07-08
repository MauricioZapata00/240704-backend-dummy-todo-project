package com.my.dummy.project.infrastructure.data.nosql.mongo.business.adapter;

import com.my.dummy.project.application.ports.nosql.mongo.business.TodoRepository;
import com.my.dummy.project.domain.model.business.Todo;
import com.my.dummy.project.infrastructure.data.nosql.mongo.business.document.TodoDocument;
import com.my.dummy.project.infrastructure.data.nosql.mongo.business.repository.TodoDataRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;


@ApplicationScoped
@RequiredArgsConstructor
public class TodoDataAdapter implements TodoRepository {

    private final TodoDataRepository todoDataRepository;

    @Override
    public Multi<Todo> findAllTodos() {
        return this.todoDataRepository.streamAll()
                .map(this::mapEntityToTodo);
    }

    @Override
    public Uni<Todo> saveTodo(Todo todo) {
        TodoDocument todoToStore = this.mapModelToTodoDocument(todo);
        return this.todoDataRepository.persist(todoToStore)
                .map(this::mapEntityToTodo);
    }

    private Todo mapEntityToTodo(TodoDocument document) {
        return Todo.builder()
                .id(document.getId())
                .title(document.getTitle())
                .build();
    }

    private TodoDocument mapModelToTodoDocument(Todo todo) {
        TodoDocument document = new TodoDocument();
        document.setId(todo.getId());
        document.setTitle(todo.getTitle());
        return document;
    }
}
