package com.my.dummy.project.application.ports.nosql.mongo.business;

import com.my.dummy.project.domain.model.business.Todo;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface TodoRepository {

    Multi<Todo> findAllTodos();
    Uni<Todo> saveTodo(Todo todo);
}
