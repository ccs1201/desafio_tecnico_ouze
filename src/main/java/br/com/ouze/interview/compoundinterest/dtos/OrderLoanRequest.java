package br.com.ouze.interview.compoundinterest.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record OrderLoanRequest(Long clientId,
                               Boolean isOuze,
                               @Max(value = 48, message = "Número máximo de parcelas é 48")
                               @Min(value = 12, message = "Número máximo de parcelas é 12")
                               Integer installments,
                               BigDecimal value) {
}
