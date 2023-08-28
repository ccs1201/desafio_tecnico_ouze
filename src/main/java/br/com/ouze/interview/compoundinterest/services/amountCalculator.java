package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.entities.Loan;

import java.math.BigDecimal;

public interface amountCalculator {

    BigDecimal calculate(Loan loan);
}
