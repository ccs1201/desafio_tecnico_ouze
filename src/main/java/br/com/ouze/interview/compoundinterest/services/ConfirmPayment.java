package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.dtos.PaymentConfirmationMessage;
import br.com.ouze.interview.compoundinterest.repositories.LoanInstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmPayment {

    private final LoanInstallmentRepository loanInstallmentRepository;

    public void execute(final PaymentConfirmationMessage message) {
        final var installments = this.loanInstallmentRepository.findByLoanIdAndWasPayedFalse(message.loanId());
        if (installments.isEmpty()) return;
        for (final var installment : installments.subList(0, message.installmentsPayed())) {
            installment.setWasPayed(Boolean.TRUE);
            this.loanInstallmentRepository.save(installment);
        }
    }
}
