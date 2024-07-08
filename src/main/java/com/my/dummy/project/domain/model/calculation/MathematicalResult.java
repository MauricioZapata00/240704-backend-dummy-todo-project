package com.my.dummy.project.domain.model.calculation;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@RegisterForReflection
public class MathematicalResult {
    private String id;
    private Double result;
}
