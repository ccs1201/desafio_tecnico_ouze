package br.com.ouze.interview.compoundinterest.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.*;

@Data
@Table(name = "loan", schema = "compound_interest")
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Loan extends BaseEntity {

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "is_ouze", nullable = false, columnDefinition = "bit DEFAULT 0")
    private Boolean isOuze;

    @Column(name = "total_installments", nullable = false)
    private Integer totalInstallments;

    @Column(name = "is_active", nullable = false, columnDefinition = "bit DEFAULT 1")
    private Boolean isActive = TRUE;

    @Column(name = "total_value", nullable = false, columnDefinition = "float")
    private BigDecimal totalValue;

    @OneToMany(mappedBy = "loan")
    private List<LoanInstallment> installments;

    public Loan(Long clientId, Boolean isOuze, Integer installments, BigDecimal value) {
        this.clientId = clientId;
        this.isOuze = isOuze;
        this.totalInstallments = installments;
        this.totalValue = value;
    }

    public BigDecimal getInstallmentsValue() {
        return this.totalValue.divide(valueOf(this.totalInstallments), HALF_UP)
                .multiply(this.getFee())
                .round(new MathContext(3, HALF_UP));
    }

    private BigDecimal getFee() {
        if (TRUE.equals(this.isOuze)) return valueOf(1.07);
        return valueOf(1.10);
    }
}
