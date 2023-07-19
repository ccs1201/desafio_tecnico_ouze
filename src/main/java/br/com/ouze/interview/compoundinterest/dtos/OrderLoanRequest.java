package br.com.ouze.interview.compoundinterest.dtos;

import java.math.BigDecimal;

public record OrderLoanRequest(Long clientId, Boolean isOuze, Integer installments, BigDecimal value) {
}
