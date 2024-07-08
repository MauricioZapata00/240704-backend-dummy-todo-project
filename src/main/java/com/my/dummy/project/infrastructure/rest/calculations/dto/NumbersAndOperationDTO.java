package com.my.dummy.project.infrastructure.rest.calculations.dto;

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
public class NumbersAndOperationDTO {

    @JsonProperty(value = "firstNumber", required = true)
    private Double firstNumber;

    @JsonProperty(value = "secondNumber", required = true)
    private Double secondNumber;

    @JsonProperty(value = "operation", required = true)
    private String operation;
}
