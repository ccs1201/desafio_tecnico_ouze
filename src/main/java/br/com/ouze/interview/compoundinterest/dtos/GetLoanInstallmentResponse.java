package br.com.ouze.interview.compoundinterest.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetLoanInstallmentResponse(Integer installment, Boolean wasPayed, BigDecimal value, LocalDate dueDate) {
}
