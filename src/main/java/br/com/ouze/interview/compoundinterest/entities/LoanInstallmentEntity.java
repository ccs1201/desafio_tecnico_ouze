package br.com.ouze.interview.compoundinterest.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table(name = "loan_installment", schema = "compound_interest")
@Entity
@EqualsAndHashCode(callSuper = true)
public class LoanInstallmentEntity extends BaseEntity {

    @Column(nullable = false)
    private Integer installment;

    @Column(name = "installment_value", nullable = false, columnDefinition = "decimal")
    private BigDecimal value;

    @Column(name = "due_date", nullable = false)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private LoanEntity loan;

    public LoanInstallmentEntity(Integer installment, BigDecimal value, LocalDate dueDate, LoanEntity loan) {
        this.installment = installment;
        this.value = value;
        this.dueDate = dueDate;
        this.loan = loan;
    }
}
