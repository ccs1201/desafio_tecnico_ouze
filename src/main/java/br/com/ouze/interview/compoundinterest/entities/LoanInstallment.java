package br.com.ouze.interview.compoundinterest.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table(name = "loan_installment", schema = "compound_interest")
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LoanInstallment extends BaseEntity {

    @Column(nullable = false)
    private Integer installment;

    @Column(name = "installment_value", nullable = false, columnDefinition = "float")
    private BigDecimal value;

    @Column(name = "due_date", nullable = false)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dueDate;

    @Column(name = "was_payed", nullable = false, columnDefinition = "bit DEFAULT 0")
    private Boolean wasPayed = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    public LoanInstallment(Integer installment, BigDecimal value, LocalDate dueDate, Loan loan) {
        this.installment = installment;
        this.value = value;
        this.dueDate = dueDate;
        this.loan = loan;
    }
}
