package br.com.ouze.interview.compoundinterest.dtos;

import br.com.ouze.interview.compoundinterest.entities.LoanInstallment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanInstallmentResponse(
        Integer installment,
        BigDecimal value,
        LocalDate dueDate,
        Boolean wasPayed
) {

    public LoanInstallmentResponse(LoanInstallment i) {
        this(i.getInstallment(), i.getValue(), i.getDueDate(), i.getWasPayed());
    }
}
