package com.my.dummy.project.infrastructure.data.nosql.mongo.business.repository;

import com.my.dummy.project.infrastructure.data.nosql.mongo.business.document.TodoDocument;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TodoDataRepository implements ReactivePanacheMongoRepositoryBase<TodoDocument, String> {
}
