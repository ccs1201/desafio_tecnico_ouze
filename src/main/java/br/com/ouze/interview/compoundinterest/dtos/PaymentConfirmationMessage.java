package br.com.ouze.interview.compoundinterest.dtos;

public record PaymentConfirmationMessage(Long loanId, Integer installmentsPayed) {
}
