package com.my.dummy.project.domain.service.calculations;

import com.my.dummy.project.application.ports.sql.oracle.calculations.MathematicalResultRepository;
import com.my.dummy.project.application.ports.sql.sqlserver.calculations.MathematicalResultMSRepository;
import com.my.dummy.project.application.useCase.calculations.ValidateOperationUseCase;
import com.my.dummy.project.domain.exceptions.calculations.InvalidOperationException;
import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class SaveMathematicalOperationUseCaseImplTest {

    @Mock
    private MathematicalResultRepository mathematicalResultRepositoryMock;

//    @Mock
//    private MathematicalResultMSRepository mathematicalResultMSRepositoryMock;

    @Mock
    private ValidateOperationUseCase validateOperationUseCaseMock;

    @InjectMocks
    private SaveMathematicalOperationUseCaseImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest.init();
    }

    @Test
    void shouldThrowsExceptionWhenOperationIsNotValid() {
        String invalidOperation = "invalidOperation";
        InvalidOperationException expectedException = InvalidOperationException.InvalidOperationType
                .OPERATION_NOT_ALLOWED.build(new Throwable("Error in test: " + invalidOperation));

        var underTestResult = underTest.process(1.0, 2.0, invalidOperation)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(InvalidOperationException.class, expectedException.getMessage())
                .getFailure();

        Assertions.assertNotNull(underTestResult);
        Assertions.assertEquals(expectedException.getMessage(), underTestResult.getMessage());

        Mockito.verify(validateOperationUseCaseMock, Mockito.never())
                .process(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString());
        Mockito.verify(mathematicalResultRepositoryMock, Mockito.never())
                .save(Mockito.any(MathematicalResult.class));
//        Mockito.verify(mathematicalResultMSRepositoryMock, Mockito.never())
//                .save(Mockito.any(MathematicalResult.class));
    }

    @Test
    void shouldThrowsExceptionWhenOperationIsDivisionByZero() {
        String operationForTest = "DIVIDE";
        InvalidOperationException expectedException = InvalidOperationException.InvalidOperationType
                .DIVISION_BY_ZERO.build(new Throwable("Error in test: " + operationForTest));
        Mockito.when(validateOperationUseCaseMock.process(Mockito.anyDouble(),
                Mockito.anyDouble(), Mockito.anyString()))
                .thenReturn(Boolean.FALSE);

        var underTestResult = underTest.process(1.0, 2.0, operationForTest)
                .subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertFailedWith(InvalidOperationException.class, expectedException.getMessage())
                .getFailure();

        Assertions.assertNotNull(underTestResult);
        Assertions.assertEquals(expectedException.getMessage(), underTestResult.getMessage());

        Mockito.verify(validateOperationUseCaseMock, Mockito.times(1))
                .process(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString());
        Mockito.verify(mathematicalResultRepositoryMock, Mockito.never())
                .save(Mockito.any(MathematicalResult.class));
//        Mockito.verify(mathematicalResultMSRepositoryMock, Mockito.never())
//                .save(Mockito.any(MathematicalResult.class));
    }

    @Test
    void shouldStoresMathematicalOperationForDivision() {
        String operationForTest = "DIVIDE";
        Double firstNumberForTest = 1.0;
        Double secondNumberForTest = 2.0;
        MathematicalResult expectedResult = MathematicalResult.builder()
                .result(firstNumberForTest / secondNumberForTest)
                .build();
        Mockito.when(validateOperationUseCaseMock.process(Mockito.anyDouble(),
                        Mockito.anyDouble(), Mockito.anyString()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(mathematicalResultRepositoryMock.save(Mockito.any(MathematicalResult.class)))
                .thenReturn(Uni.createFrom().item(expectedResult));
//        Mockito.when(mathematicalResultMSRepositoryMock.save(Mockito.any(MathematicalResult.class)))
//                .thenReturn(Uni.createFrom().item(expectedResult));

        underTest.process(firstNumberForTest, secondNumberForTest, operationForTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(expectedResult.getResult(), underTestResult.getResult());
                });


        Mockito.verify(validateOperationUseCaseMock, Mockito.times(1))
                .process(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString());
        Mockito.verify(mathematicalResultRepositoryMock, Mockito.times(1))
                .save(Mockito.any(MathematicalResult.class));
//        Mockito.verify(mathematicalResultMSRepositoryMock, Mockito.times(1))
//                .save(Mockito.any(MathematicalResult.class));
    }

    @Test
    void shouldStoresMathematicalOperationForAddition() {
        String operationForTest = "ADD";
        Double firstNumberForTest = 1.0;
        Double secondNumberForTest = 2.0;
        MathematicalResult expectedResult = MathematicalResult.builder()
                .result(firstNumberForTest + secondNumberForTest)
                .build();
        Mockito.when(validateOperationUseCaseMock.process(Mockito.anyDouble(),
                        Mockito.anyDouble(), Mockito.anyString()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(mathematicalResultRepositoryMock.save(Mockito.any(MathematicalResult.class)))
                .thenReturn(Uni.createFrom().item(expectedResult));
//        Mockito.when(mathematicalResultMSRepositoryMock.save(Mockito.any(MathematicalResult.class)))
//                .thenReturn(Uni.createFrom().item(expectedResult));

        underTest.process(firstNumberForTest, secondNumberForTest, operationForTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(expectedResult.getResult(), underTestResult.getResult());
                });


        Mockito.verify(validateOperationUseCaseMock, Mockito.times(1))
                .process(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString());
        Mockito.verify(mathematicalResultRepositoryMock, Mockito.times(1))
                .save(Mockito.any(MathematicalResult.class));
//        Mockito.verify(mathematicalResultMSRepositoryMock, Mockito.times(1))
//                .save(Mockito.any(MathematicalResult.class));
    }

    @Test
    void shouldStoresMathematicalOperationForSubtraction() {
        String operationForTest = "SUBTRACT";
        Double firstNumberForTest = 1.0;
        Double secondNumberForTest = 2.0;
        MathematicalResult expectedResult = MathematicalResult.builder()
                .result(firstNumberForTest - secondNumberForTest)
                .build();
        Mockito.when(validateOperationUseCaseMock.process(Mockito.anyDouble(),
                        Mockito.anyDouble(), Mockito.anyString()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(mathematicalResultRepositoryMock.save(Mockito.any(MathematicalResult.class)))
                .thenReturn(Uni.createFrom().item(expectedResult));
//        Mockito.when(mathematicalResultMSRepositoryMock.save(Mockito.any(MathematicalResult.class)))
//                .thenReturn(Uni.createFrom().item(expectedResult));

        underTest.process(firstNumberForTest, secondNumberForTest, operationForTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(expectedResult.getResult(), underTestResult.getResult());
                });


        Mockito.verify(validateOperationUseCaseMock, Mockito.times(1))
                .process(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString());
        Mockito.verify(mathematicalResultRepositoryMock, Mockito.times(1))
                .save(Mockito.any(MathematicalResult.class));
//        Mockito.verify(mathematicalResultMSRepositoryMock, Mockito.times(1))
//                .save(Mockito.any(MathematicalResult.class));
    }

    @Test
    void shouldStoresMathematicalOperationForMultiplication() {
        String operationForTest = "MULTIPLY";
        Double firstNumberForTest = 1.0;
        Double secondNumberForTest = 2.0;
        MathematicalResult expectedResult = MathematicalResult.builder()
                .result(firstNumberForTest * secondNumberForTest)
                .build();
        Mockito.when(validateOperationUseCaseMock.process(Mockito.anyDouble(),
                        Mockito.anyDouble(), Mockito.anyString()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(mathematicalResultRepositoryMock.save(Mockito.any(MathematicalResult.class)))
                .thenReturn(Uni.createFrom().item(expectedResult));
//        Mockito.when(mathematicalResultMSRepositoryMock.save(Mockito.any(MathematicalResult.class)))
//                .thenReturn(Uni.createFrom().item(expectedResult));

        underTest.process(firstNumberForTest, secondNumberForTest, operationForTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(expectedResult.getResult(), underTestResult.getResult());
                });


        Mockito.verify(validateOperationUseCaseMock, Mockito.times(1))
                .process(Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString());
        Mockito.verify(mathematicalResultRepositoryMock, Mockito.times(1))
                .save(Mockito.any(MathematicalResult.class));
//        Mockito.verify(mathematicalResultMSRepositoryMock, Mockito.times(1))
//                .save(Mockito.any(MathematicalResult.class));
    }
}
