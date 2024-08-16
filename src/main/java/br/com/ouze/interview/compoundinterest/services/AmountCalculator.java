package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.entities.Loan;

import java.math.BigDecimal;

public interface AmountCalculator {

    BigDecimal calculate(Loan loan);
}
