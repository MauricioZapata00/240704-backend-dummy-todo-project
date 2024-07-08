package com.my.dummy.project.application.ports.sql.sqlserver.calculations;

import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import io.smallrye.mutiny.Uni;

public interface MathematicalResultMSRepository {
    Uni<MathematicalResult> save(MathematicalResult mathematicalResult);
}
