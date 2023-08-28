package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.dtos.PaymentDTO;
import br.com.ouze.interview.compoundinterest.entities.LoanInstallment;
import br.com.ouze.interview.compoundinterest.exceptions.InternalServiceException;
import br.com.ouze.interview.compoundinterest.exceptions.NotFoundException;
import br.com.ouze.interview.compoundinterest.repositories.LoanInstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final LoanInstallmentRepository repository;
    private final LoanService loanService;

    @Transactional
    public void payInstallment(PaymentDTO paymentDTO) {

        var installment = repository.findByLoan_IdAndInstallment(paymentDTO.loanId(), paymentDTO.installmentNumber())
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format("O empréstimo informado não possui parcela de nº %d", paymentDTO.installmentNumber())));
        try {
            installment.setWasPayed(Boolean.TRUE);

            save(installment);

            checkIfAllInstallmentsWasPaid(paymentDTO.loanId());
        } catch (Exception e) {
            throw new InternalServiceException("Não foi possível efetuar o pagamento da parcela.", e);
        }
    }

    private void checkIfAllInstallmentsWasPaid(Long loanId) {
        var noPayedinstallments = repository.findByLoan_IdAndWasPayedFalse(loanId);

        if (noPayedinstallments.isEmpty()) {
            loanService.setInactive(loanId);
        }
    }


    private void save(LoanInstallment loanInstallment) {
        repository.save(loanInstallment);
    }
}
