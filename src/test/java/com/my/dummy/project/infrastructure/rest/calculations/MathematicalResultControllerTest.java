package com.my.dummy.project.infrastructure.rest.calculations;

import com.my.dummy.project.application.useCase.calculations.SaveMathematicalOperationUseCase;
import com.my.dummy.project.domain.exceptions.calculations.InvalidOperationException;
import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import com.my.dummy.project.infrastructure.rest.calculations.dto.NumbersAndOperationDTO;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class MathematicalResultControllerTest {

    @Mock
    private SaveMathematicalOperationUseCase saveMathematicalOperationUseCaseMock;

    @InjectMocks
    private MathematicalResultController underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveMathematicalOperationShouldReturnsInternalServerError() {
        String errorString = "An error occurred while retrieving all todos in test";
        Response expectedResponse = Response.status(500)
                .entity(errorString)
                .build();
        NumbersAndOperationDTO dtoForTest = new NumbersAndOperationDTO();
        dtoForTest.setOperation("ADD");
        dtoForTest.setFirstNumber(1.0);
        dtoForTest.setSecondNumber(2.0);
        Mockito.when(saveMathematicalOperationUseCaseMock.process(dtoForTest.getFirstNumber(),
                        dtoForTest.getSecondNumber(), dtoForTest.getOperation()))
                .thenReturn(Uni.createFrom().failure(new Throwable(errorString)));

        underTest.saveMathematicalOperation(dtoForTest)
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                    Assertions.assertEquals(expectedResponse.getEntity(), underTestResponse.getEntity());
                });

        Mockito.verify(saveMathematicalOperationUseCaseMock, Mockito.times(1))
                .process(dtoForTest.getFirstNumber(), dtoForTest.getSecondNumber(),
                        dtoForTest.getOperation());
    }

    @Test
    void saveMathematicalOperationShouldReturnsBadRequest() {
        String errorString = "An error occurred while retrieving all todos in test";
        Response expectedResponse = Response.status(400)
                .entity(errorString)
                .build();
        NumbersAndOperationDTO dtoForTest = new NumbersAndOperationDTO();
        dtoForTest.setOperation("Something");
        dtoForTest.setFirstNumber(1.0);
        dtoForTest.setSecondNumber(2.0);
        Mockito.when(saveMathematicalOperationUseCaseMock.process(dtoForTest.getFirstNumber(),
                        dtoForTest.getSecondNumber(), dtoForTest.getOperation()))
                .thenReturn(Uni.createFrom().failure(InvalidOperationException.InvalidOperationType
                        .OPERATION_NOT_ALLOWED.build(new Throwable(errorString))));

        underTest.saveMathematicalOperation(dtoForTest)
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                });

        Mockito.verify(saveMathematicalOperationUseCaseMock, Mockito.times(1))
                .process(dtoForTest.getFirstNumber(), dtoForTest.getSecondNumber(),
                        dtoForTest.getOperation());
    }

    @Test
    void saveMathematicalOperationShouldReturnsCreatedStatus() {
        NumbersAndOperationDTO dtoForTest = new NumbersAndOperationDTO();
        dtoForTest.setOperation("ADD");
        dtoForTest.setFirstNumber(1.0);
        dtoForTest.setSecondNumber(2.0);
        Response expectedResponse = Response.status(201)
                .entity(dtoForTest)
                .build();
        MathematicalResult mockedMathematicalResult = MathematicalResult.builder()
                .id(UUID.randomUUID().toString())
                .result(dtoForTest.getFirstNumber() + dtoForTest.getSecondNumber())
                .build();
        Mockito.when(saveMathematicalOperationUseCaseMock.process(dtoForTest.getFirstNumber(),
                        dtoForTest.getSecondNumber(), dtoForTest.getOperation()))
                .thenReturn(Uni.createFrom().item(mockedMathematicalResult));

        underTest.saveMathematicalOperation(dtoForTest)
                .subscribe().with(underTestResponse -> {
                    Assertions.assertNotNull(underTestResponse);
                    Assertions.assertEquals(expectedResponse.getStatus(), underTestResponse.getStatus());
                });

        Mockito.verify(saveMathematicalOperationUseCaseMock, Mockito.times(1))
                .process(dtoForTest.getFirstNumber(), dtoForTest.getSecondNumber(),
                        dtoForTest.getOperation());
    }
}
