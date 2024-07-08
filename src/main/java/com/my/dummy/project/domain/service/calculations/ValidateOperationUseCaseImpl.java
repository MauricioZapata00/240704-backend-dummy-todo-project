package com.my.dummy.project.domain.service.calculations;

import com.my.dummy.project.application.useCase.calculations.ValidateOperationUseCase;
import com.my.dummy.project.domain.model.calculation.OperationEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;


@ApplicationScoped
@RequiredArgsConstructor
public class ValidateOperationUseCaseImpl implements ValidateOperationUseCase {

    private static final Double ZERO_DOUBLE_VALUE = 0.0;

    @Override
    public Boolean process(Double firstNumber, Double secondNumber, String operationName) {
        return !(operationName.equals(OperationEnum.DIVIDE.name()) && ZERO_DOUBLE_VALUE.equals(secondNumber));
    }
}
