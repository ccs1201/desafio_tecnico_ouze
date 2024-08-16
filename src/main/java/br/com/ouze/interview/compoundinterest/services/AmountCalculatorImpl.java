package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.entities.Loan;
import br.com.ouze.interview.compoundinterest.formatters.MonetaryFormatter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountCalculatorImpl implements AmountCalculator {

    @Override
    public BigDecimal calculate(Loan loan) {
        return calculateTotalAmount(loan);
    }


    private BigDecimal calculateTotalAmount(Loan loan) {

        return MonetaryFormatter.format(loan.getTotalValue().multiply(getInterestRate(loan)));
    }


    private BigDecimal getInterestRate(Loan loan) {

        return BigDecimal.valueOf(Math.pow(loan.getCompoundFee().doubleValue(), loan.getTotalInstallments()));
    }
}
