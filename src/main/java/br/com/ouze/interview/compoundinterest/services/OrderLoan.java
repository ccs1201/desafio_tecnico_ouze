package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.dtos.OrderLoanRequest;
import br.com.ouze.interview.compoundinterest.dtos.OrderLoanResponse;
import br.com.ouze.interview.compoundinterest.entities.Loan;
import br.com.ouze.interview.compoundinterest.entities.LoanInstallment;
import br.com.ouze.interview.compoundinterest.exceptions.BadRequestException;
import br.com.ouze.interview.compoundinterest.exceptions.InternalServerErrorException;
import br.com.ouze.interview.compoundinterest.repositories.LoanInstallmentRepository;
import br.com.ouze.interview.compoundinterest.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderLoan {

    private final LoanRepository loanRepository;

    private final LoanInstallmentRepository loanInstallmentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {InternalServerErrorException.class})
    public OrderLoanResponse execute(final OrderLoanRequest orderLoanRequest) throws ResponseStatusException {
        this.checkIfThereIsActiveContract(orderLoanRequest.clientId());
        try {
            final var loan = new Loan(
                    orderLoanRequest.clientId(),
                    orderLoanRequest.isOuze(),
                    orderLoanRequest.installments(),
                    orderLoanRequest.value()
            );
            final var loanSaved = this.loanRepository.save(loan);
            this.createInstallments(loanSaved);
            return new OrderLoanResponse(loanSaved.getClientId(), loanSaved.getTotalInstallments(), loanSaved.getTotalValue());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    private void checkIfThereIsActiveContract(final Long clientId) throws ResponseStatusException {
        final var hasContractActive = this.loanRepository.findFirstByClientIdAndIsActiveTrueOrderByCreateAtDesc(clientId)
                .isPresent();
        if (hasContractActive)
            throw new BadRequestException(String.format("Já existe um contrato ativo para esse cliente %d", clientId));
    }

    private void createInstallments(final Loan loan) {
        final var installments = new ArrayList<LoanInstallment>();
        final var value = loan.getInstallmentsValue();
        for (var i = 1; i <= loan.getTotalInstallments(); i++) {
            installments.add(new LoanInstallment(i, value, LocalDate.now().plus(i, ChronoUnit.MONTHS), loan));
        }
        this.loanInstallmentRepository.saveAll(installments);
    }

}
