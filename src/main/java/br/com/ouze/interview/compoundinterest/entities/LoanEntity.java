package br.com.ouze.interview.compoundinterest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Table(name = "loan", schema = "compound_interest")
@Entity
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class LoanEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(name = "is_ouze", nullable = false, columnDefinition = "bit DEFAULT 0")
    private Boolean isOuze;

    @Column(name = "total_installments", nullable = false)
    private Integer totalInstallments;

    @Column(name = "total_value", nullable = false, columnDefinition = "decimal")
    private BigDecimal totalValue;
}
