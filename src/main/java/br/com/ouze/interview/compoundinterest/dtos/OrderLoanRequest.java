package br.com.ouze.interview.compoundinterest.dtos;

import java.math.BigDecimal;

public record OrderLoanRequest(String cpf, Boolean isOuze, Integer installments, BigDecimal value) {
}
