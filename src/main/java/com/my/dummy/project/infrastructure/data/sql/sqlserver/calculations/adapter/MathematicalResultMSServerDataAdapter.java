package com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.adapter;

import com.my.dummy.project.application.ports.sql.oracle.calculations.MathematicalResultRepository;
import com.my.dummy.project.application.ports.sql.sqlserver.calculations.MathematicalResultMSRepository;
import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.entity.MathematicalResultMSServerEntity;
import com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.repository.MathematicalResultMSServerDataRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@RequiredArgsConstructor
public class MathematicalResultMSServerDataAdapter {


    private final AtomicLong counter = new AtomicLong();

    private final MathematicalResultMSServerDataRepository mathematicalResultDataRepository;

//    @Override
    public Uni<MathematicalResult> save(MathematicalResult mathematicalResult) {
        MathematicalResultMSServerEntity entityToStore = this.mapModelToEntity(mathematicalResult);
        return mathematicalResultDataRepository.persist(entityToStore)
                .map(this::mapEntityToModel);
    }

    private MathematicalResultMSServerEntity mapModelToEntity(MathematicalResult mathematicalResult) {
        MathematicalResultMSServerEntity entity = new MathematicalResultMSServerEntity();
        entity.setId(counter.incrementAndGet());
        entity.setResult(mathematicalResult.getResult());
        return entity;
    }

    private MathematicalResult mapEntityToModel(MathematicalResultMSServerEntity entity) {
        return MathematicalResult.builder()
                .id(String.valueOf(entity.getId()))
                .result(entity.getResult())
                .build();
    }
}
