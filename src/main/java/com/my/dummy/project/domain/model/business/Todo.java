package com.my.dummy.project.domain.model.business;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder(toBuilder = true)
@RegisterForReflection
@ToString
public class Todo {
    private String id;
    private String title;
}
