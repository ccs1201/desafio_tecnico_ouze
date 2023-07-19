package br.com.ouze.interview.compoundinterest.dtos;

import java.math.BigDecimal;

public record OrderLoanResponse(Long clientId, Integer installments, BigDecimal value) {
}
