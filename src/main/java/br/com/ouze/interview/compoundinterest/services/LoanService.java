package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.entities.Loan;
import br.com.ouze.interview.compoundinterest.entities.LoanInstallment;
import br.com.ouze.interview.compoundinterest.exceptions.ClienteComContrativoAtivoException;
import br.com.ouze.interview.compoundinterest.exceptions.InternalServiceException;
import br.com.ouze.interview.compoundinterest.formatters.MonetaryFormatter;
import br.com.ouze.interview.compoundinterest.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository repository;
    private final AmountCalculator amountCalculator;

    @Transactional
    public Loan createLoan(Loan loan) {
        verifyAciveContract(loan.getClientId());
        try {
            buildLoanInstallments(loan);
            return repository.save(loan);
        } catch (Exception e) {
            throw new InternalServiceException("Erro ao cadastrar empréstimo.", e);
        }

    }

    private void verifyAciveContract(Long clientId) {

        if (repository.findFirstByClientIdAndIsActiveTrue(clientId).isPresent()) {
            throw new ClienteComContrativoAtivoException(String.format("Cliente %d já possui contrato ativo", clientId));
        }
    }

    private void buildLoanInstallments(Loan loan) {

        var installmentValue = MonetaryFormatter.format(amountCalculator.calculate(loan).divide(BigDecimal.valueOf(loan.getTotalInstallments())));

        loan.setInstallments(new ArrayList<>());

        var now = LocalDate.now();

        for (int i = 1; i <= loan.getTotalInstallments(); i++) {

            var installment = new LoanInstallment();

            installment.setLoan(loan);
            installment.setDueDate(now.plusMonths(i));
            installment.setInstallment(i);
            installment.setValue(installmentValue);

            loan.getInstallments().add(installment);
        }
    }

    public void setInactive(Long loanId) {

        try {
            var loan = repository.findById(loanId).get();

            loan.setIsActive(Boolean.FALSE);

            repository.saveAndFlush(loan);
        } catch (Exception e) {
            throw new InternalServiceException("Erro ao tentar inativar contrato", e);
        }
    }
}
