package com.my.dummy.project.application.useCase.calculations;

@FunctionalInterface
public interface ValidateOperationUseCase {

    Boolean process(Double firstNumber, Double secondNumber, String operationName);
}
