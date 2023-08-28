package br.com.ouze.interview.compoundinterest.formatters;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class MonetaryFormatter {

    public BigDecimal format(BigDecimal value) {

        return value.plus().setScale(2, RoundingMode.HALF_UP);
    }
}
