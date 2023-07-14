package br.com.ouze.interview.compoundinterest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@Table(name = "loan", schema = "compound_interest")
@Entity
@EqualsAndHashCode(callSuper = true)
public class LoanEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(name = "is_ouze", nullable = false, columnDefinition = "bit DEFAULT 0")
    private Boolean isOuze;

    @Column(name = "total_installments", nullable = false)
    private Integer totalInstallments;

    @Column(name = "total_value", nullable = false, columnDefinition = "decimal")
    private BigDecimal totalValue;

    @OneToMany(mappedBy = "loan")
    private List<LoanInstallmentEntity> installments;

    public LoanEntity(String cpf, Boolean isOuze, Integer installments, BigDecimal value) {
        this.cpf = cpf;
        this.isOuze = isOuze;
        this.totalInstallments = installments;
        this.totalValue = value;
    }
}
