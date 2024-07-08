package com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.adapter;

import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.entity.MathematicalResultMSServerEntity;
import com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.repository.MathematicalResultMSServerDataRepository;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

class MathematicalResultMSServerDataAdapterTest {

    @Mock
    private MathematicalResultMSServerDataRepository mathematicalResultDataRepositoryMock;

    @InjectMocks
    private MathematicalResultMSServerDataAdapter underTest;

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
        MathematicalResultMSServerEntity mockedEntity = new MathematicalResultMSServerEntity();
        mockedEntity.setResult(modelForTest.getResult());

        Mockito.when(mathematicalResultDataRepositoryMock.persist(Mockito.any(MathematicalResultMSServerEntity.class)))
                        .thenReturn(Uni.createFrom().item(mockedEntity));

        underTest.save(modelForTest)
                .subscribe().with(underTestResult -> {
                    Assertions.assertNotNull(underTestResult);
                    Assertions.assertEquals(modelForTest.getResult(), underTestResult.getResult());
                });

        Mockito.verify(mathematicalResultDataRepositoryMock, Mockito.times(1))
                .persist(Mockito.any(MathematicalResultMSServerEntity.class));
    }
}
