package com.my.dummy.project.domain.service.calculations;

import com.my.dummy.project.application.ports.sql.oracle.calculations.MathematicalResultRepository;
import com.my.dummy.project.application.ports.sql.sqlserver.calculations.MathematicalResultMSRepository;
import com.my.dummy.project.application.useCase.calculations.SaveMathematicalOperationUseCase;
import com.my.dummy.project.application.useCase.calculations.ValidateOperationUseCase;
import com.my.dummy.project.domain.exceptions.calculations.InvalidOperationException;
import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import com.my.dummy.project.domain.model.calculation.OperationEnum;
import io.smallrye.mutiny.Uni;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class SaveMathematicalOperationUseCaseImpl implements SaveMathematicalOperationUseCase {

    private final MathematicalResultRepository mathematicalResultRepository;
    private final MathematicalResultMSRepository mathematicalResultMSRepository;
    private final ValidateOperationUseCase validateOperationUseCase;

    private final Map<String, BiFunction<Double, Double, Double>> validOperations = new HashMap<>();

    @PostConstruct
    public void init() {
        validOperations.put(OperationEnum.ADD.name(), Double::sum);
        validOperations.put(OperationEnum.SUBTRACT.name(), this.getSubtraction());
        validOperations.put(OperationEnum.MULTIPLY.name(), this.getMultiplication());
        validOperations.put(OperationEnum.DIVIDE.name(), this.getDivision());
    }

    private BiFunction<Double, Double, Double> getSubtraction() {
        return (num1, num2) -> num1 - num2;
    }

    private BiFunction<Double, Double, Double> getMultiplication() {
        return (num1, num2) -> num1 * num2;
    }

    private BiFunction<Double, Double, Double> getDivision() {
        return (num1, num2) -> num1 / num2;
    }

    @Override
    public Uni<MathematicalResult> process(Double firstNumber, Double secondNumber, String operationName) {
        return Uni.createFrom().item(operationName)
                .map(this::checkOperationNameInValidOperationKeysMap)
                .flatMap(unusedBoolean -> this.validateDivisionAndPersistCalculation(firstNumber, secondNumber, operationName))
                .onFailure(InvalidOperationException.class).invoke(throwable -> log.error(throwable.getMessage()));
    }

    private Boolean checkOperationNameInValidOperationKeysMap(String operationName) {
        if (Boolean.FALSE.equals(validOperations.containsKey(operationName))){
            throw InvalidOperationException.InvalidOperationType
                    .OPERATION_NOT_ALLOWED.build(new Throwable("Unable to map operation: " + operationName));
        }
        return validOperations.containsKey(operationName);
    }

    private Uni<MathematicalResult> validateDivisionAndPersistCalculation(Double firstNumber, Double secondNumber, String operationName) {
        if (Boolean.FALSE.equals(validateOperationUseCase.process(firstNumber, secondNumber, operationName))){
            throw InvalidOperationException.InvalidOperationType
                    .DIVISION_BY_ZERO.build(new Throwable("Division by zero is not defined"));
        }
        return mathematicalResultRepository.save(MathematicalResult.builder()
                        .id(UUID.randomUUID().toString())
                        .result(validOperations.get(operationName).apply(firstNumber, secondNumber))
                .build())
                .flatMap(this.mathematicalResultMSRepository::save);
    }


}
