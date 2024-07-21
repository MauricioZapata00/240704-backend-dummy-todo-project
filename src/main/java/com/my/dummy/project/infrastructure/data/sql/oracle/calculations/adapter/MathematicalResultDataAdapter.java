package com.my.dummy.project.infrastructure.data.sql.oracle.calculations.adapter;

import com.my.dummy.project.application.ports.sql.oracle.calculations.MathematicalResultRepository;
import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import com.my.dummy.project.infrastructure.data.sql.oracle.calculations.entity.MathematicalResultEntity;
import com.my.dummy.project.infrastructure.data.sql.oracle.calculations.repository.MathematicalResultDataRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;


@ApplicationScoped
@RequiredArgsConstructor
public class MathematicalResultDataAdapter implements MathematicalResultRepository {


    private final MathematicalResultDataRepository mathematicalResultDataRepository;

    @Override
    @WithTransaction
    public Uni<MathematicalResult> save(MathematicalResult mathematicalResult) {
        MathematicalResultEntity entityToStore = this.mapModelToEntity(mathematicalResult);
        return mathematicalResultDataRepository.persistAndFlush(entityToStore)
                .map(this::mapEntityToModel);
    }

    private MathematicalResultEntity mapModelToEntity(MathematicalResult mathematicalResult) {
        MathematicalResultEntity entity = new MathematicalResultEntity();
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
