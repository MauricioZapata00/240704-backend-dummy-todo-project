package com.my.dummy.project.infrastructure.data.sql.oracle.calculations.adapter;

import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import com.my.dummy.project.infrastructure.data.sql.oracle.calculations.entity.MathematicalResultEntity;
import com.my.dummy.project.infrastructure.data.sql.oracle.calculations.repository.MathematicalResultDataRepository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class MathematicalResultDataAdapterTest {

    @Mock
    private MathematicalResultDataRepository mathematicalResultDataRepositoryMock;

    @InjectMocks
    private MathematicalResultDataAdapter underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldStoresMathematicalResult() {
        MathematicalResult modelForTest = MathematicalResult.builder()
                .id(UUID.randomUUID().toString())
                .result(3.5)
                .build();
        MathematicalResultEntity mockedEntity = new MathematicalResultEntity();
        mockedEntity.setId(45L);
        mockedEntity.setResult(modelForTest.getResult());

        Mockito.when(mathematicalResultDataRepositoryMock.persistAndFlush(Mockito.any(MathematicalResultEntity.class)))
                        .thenReturn(Uni.createFrom().item(mockedEntity));

        underTest.save(modelForTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(modelForTest.getResult(), underTestResult.getResult());
                });

        Mockito.verify(mathematicalResultDataRepositoryMock, Mockito.times(1))
                .persistAndFlush(Mockito.any(MathematicalResultEntity.class));
    }
}
