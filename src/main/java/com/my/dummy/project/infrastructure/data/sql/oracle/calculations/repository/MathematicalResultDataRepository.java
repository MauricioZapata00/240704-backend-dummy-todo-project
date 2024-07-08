package com.my.dummy.project.infrastructure.data.sql.oracle.calculations.repository;

import com.my.dummy.project.infrastructure.data.sql.oracle.calculations.entity.MathematicalResultEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MathematicalResultDataRepository implements PanacheRepositoryBase<MathematicalResultEntity, Long> {
}
