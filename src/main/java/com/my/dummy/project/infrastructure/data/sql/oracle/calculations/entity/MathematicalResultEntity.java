package com.my.dummy.project.infrastructure.data.sql.oracle.calculations.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mathematical_results")
@Getter
@Setter
@NoArgsConstructor
public class MathematicalResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mathematical_result_id")
    private Long id;
    @Column(name = "result")
    private Double result;
}
