package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.entities.Loan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class AmountCalculatorImplTest {

    private Loan loan;
    @InjectMocks
    private AmountCalculatorImpl amountCalculator;


    @BeforeEach
    void setup() {
        loan = new Loan();

        loan.setIsOuze(Boolean.FALSE);
        loan.setTotalValue(BigDecimal.valueOf(1000));
        loan.setTotalInstallments(48);
    }

    @Test
    void testCalculateWhenIsNotOuzeShouldPass() {

        var expected = BigDecimal.valueOf(1612.23);

        var actual = amountCalculator.calculate(loan);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void testCalculateWhenIstOuzeShouldPass() {

        loan.setIsOuze(Boolean.TRUE);

        var expected = BigDecimal.valueOf(1397.70).setScale(2);

        var actual = amountCalculator.calculate(loan);

        Assertions.assertEquals(expected, actual);

    }
}
