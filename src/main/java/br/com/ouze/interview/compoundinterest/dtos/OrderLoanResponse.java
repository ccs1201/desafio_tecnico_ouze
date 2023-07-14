package br.com.ouze.interview.compoundinterest.dtos;

import java.math.BigDecimal;

public record OrderLoanResponse(String cpf, Integer installments, BigDecimal value) {
}
