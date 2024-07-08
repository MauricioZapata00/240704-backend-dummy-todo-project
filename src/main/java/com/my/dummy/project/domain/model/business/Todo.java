package com.my.dummy.project.domain.model.business;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@RegisterForReflection
public class Todo {
    private String id;
    private String title;
}
