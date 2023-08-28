package br.com.ouze.interview.compoundinterest.dtos;

public record PaymentErrorDTO(PaymentDTO paymentDTO, Exception exception) {
}
