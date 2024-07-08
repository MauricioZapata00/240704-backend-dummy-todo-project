package com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.repository;


import com.my.dummy.project.infrastructure.data.sql.sqlserver.calculations.entity.MathematicalResultMSServerEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.session.impl.ReactiveSessionImpl;

import javax.inject.Named;

@ApplicationScoped
@RequiredArgsConstructor
public class MathematicalResultMSServerDataRepository implements PanacheRepositoryBase<MathematicalResultMSServerEntity, Long> {

}
