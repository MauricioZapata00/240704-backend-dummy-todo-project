package com.my.dummy.project.application.useCase.calculations;

import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import io.smallrye.mutiny.Uni;

@FunctionalInterface
public interface SaveMathematicalOperationUseCase {

    Uni<MathematicalResult> process(Double firstNumber, Double secondNumber, String operationName);
}
