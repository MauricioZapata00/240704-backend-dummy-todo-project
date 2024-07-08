package com.my.dummy.project.infrastructure.data.sql.oracle.calculations.adapter;

import com.my.dummy.project.application.ports.sql.oracle.calculations.MathematicalResultRepository;
import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import com.my.dummy.project.infrastructure.data.sql.oracle.calculations.entity.MathematicalResultEntity;
import com.my.dummy.project.infrastructure.data.sql.oracle.calculations.repository.MathematicalResultDataRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
@RequiredArgsConstructor
public class MathematicalResultDataAdapter implements MathematicalResultRepository {


    private final AtomicLong counter = new AtomicLong();
    private final MathematicalResultDataRepository mathematicalResultDataRepository;

    @Override
    public Uni<MathematicalResult> save(MathematicalResult mathematicalResult) {
        MathematicalResultEntity entityToStore = this.mapModelToEntity(mathematicalResult);
        return mathematicalResultDataRepository.persist(entityToStore)
                .map(this::mapEntityToModel);
    }

    private MathematicalResultEntity mapModelToEntity(MathematicalResult mathematicalResult) {
        MathematicalResultEntity entity = new MathematicalResultEntity();
        entity.setId(counter.incrementAndGet());
        entity.setResult(mathematicalResult.getResult());
        return entity;
    }

    private MathematicalResult mapEntityToModel(MathematicalResultEntity entity) {
        return MathematicalResult.builder()
                .id(String.valueOf(entity.getId()))
                .result(entity.getResult())
                .build();
    }
}
