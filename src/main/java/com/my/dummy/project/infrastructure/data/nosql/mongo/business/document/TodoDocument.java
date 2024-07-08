package com.my.dummy.project.infrastructure.data.nosql.mongo.business.document;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;

@Getter
@Setter
@NoArgsConstructor
@MongoEntity(collection = "routes")
public class TodoDocument {
    @BsonId
    private String id;
    private String title;
}
