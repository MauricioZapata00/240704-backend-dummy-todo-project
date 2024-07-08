package com.my.dummy.project.domain.service.calculations;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


class ValidateOperationUseCaseImplTest {

    @InjectMocks
    private ValidateOperationUseCaseImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processValidateOperationUseCaseImplShouldReturnsFalse(){
        var underTestResult = underTest.process(1.0, 0.0, "DIVIDE");

        Assertions.assertNotNull(underTestResult);
        Assertions.assertFalse(underTestResult);
    }

    @Test
    void processValidateOperationUseCaseImplShouldReturnsTrueForSecondNumberGreaterThanZero(){
        var underTestResult = underTest.process(1.0, 1.0, "DIVIDE");

        Assertions.assertNotNull(underTestResult);
        Assertions.assertTrue(underTestResult);
    }

    @Test
    void processValidateOperationUseCaseImplShouldReturnsTrueForAnyOperation(){
        var underTestResult = underTest.process(1.0, 0.0, "SOMETHING");

        Assertions.assertNotNull(underTestResult);
        Assertions.assertTrue(underTestResult);
    }
}
