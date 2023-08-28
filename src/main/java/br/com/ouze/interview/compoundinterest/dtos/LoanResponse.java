package br.com.ouze.interview.compoundinterest.dtos;

import br.com.ouze.interview.compoundinterest.entities.Loan;

import java.math.BigDecimal;

public record LoanResponse(
        Long id,
        Long clientId,
        Boolean isOuze,
        Integer totalInstallments,
        Boolean isActive,
        BigDecimal totalValue) {


    public LoanResponse(Loan loan) {
        this(loan.getId(),
                loan.getClientId(),
                loan.getIsOuze(),
                loan.getTotalInstallments(),
                loan.getIsActive(),
                loan.getTotalValue());
    }
}
