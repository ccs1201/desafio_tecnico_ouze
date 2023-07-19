package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.dtos.PaymentConfirmationMessage;
import br.com.ouze.interview.compoundinterest.entities.Loan;
import br.com.ouze.interview.compoundinterest.exceptions.InternalServerErrorException;
import br.com.ouze.interview.compoundinterest.exceptions.NotFoundException;
import br.com.ouze.interview.compoundinterest.repositories.LoanInstallmentRepository;
import br.com.ouze.interview.compoundinterest.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConfirmPayment {

    private final LoanInstallmentRepository loanInstallmentRepository;

    private final LoanRepository loanRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {InternalServerErrorException.class})
    public void execute(final PaymentConfirmationMessage message) throws NotFoundException {
        final var loan = this.loanRepository.findById(message.loanId())
                .orElseThrow(() -> new NotFoundException(String.format("Contrato de empréstimo não existe LoanId %d", message.loanId())));
        try {
            this.updateInstallmentPay(loan.getId(), message.installmentsPayed());
            this.checkAndFinishLoanContract(loan);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    private void updateInstallmentPay(final Long loanId, final Integer installmentsPayed) {
        final var installments = this.loanInstallmentRepository.findByLoanIdAndWasPayedFalse(loanId);
        if (installments.isEmpty()) return;
        for (final var installment : installments.subList(0, installmentsPayed)) {
            installment.setWasPayed(TRUE);
            this.loanInstallmentRepository.save(installment);
        }
    }

    private void checkAndFinishLoanContract(final Loan loan) {
        final var installments = this.loanInstallmentRepository.findByLoanIdAndWasPayedFalse(loan.getId());
        if (installments.isEmpty()) {
            loan.setIsActive(FALSE);
            this.loanRepository.save(loan);
        }
    }
}
