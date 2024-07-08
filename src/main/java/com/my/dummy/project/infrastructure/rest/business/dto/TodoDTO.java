package com.my.dummy.project.infrastructure.rest.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@RegisterForReflection
public class TodoDTO {

    @JsonProperty(value = "id", required = true)
    private String id;

    @JsonProperty(value = "title", required = true)
    private String title;
}
