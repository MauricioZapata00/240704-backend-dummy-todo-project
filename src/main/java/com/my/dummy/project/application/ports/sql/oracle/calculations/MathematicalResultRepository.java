package com.my.dummy.project.application.ports.sql.oracle.calculations;

import com.my.dummy.project.domain.model.calculation.MathematicalResult;
import io.smallrye.mutiny.Uni;

public interface MathematicalResultRepository {

    Uni<MathematicalResult> save(MathematicalResult mathematicalResult);
}
