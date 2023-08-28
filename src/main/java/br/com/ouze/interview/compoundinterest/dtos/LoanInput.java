package br.com.ouze.interview.compoundinterest.dtos;

import br.com.ouze.interview.compoundinterest.entities.Loan;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record LoanInput(
        @Positive Long clientId,
        @NotNull Boolean isOuze,
        @Min(12) @Max(48) Integer totalInstallments,
        @Positive @NotNull BigDecimal totalValue) {


    public Loan toLoan() {
        return new Loan(clientId, isOuze, totalInstallments, totalValue);
    }
}
