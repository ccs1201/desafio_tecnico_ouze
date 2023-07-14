package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.dtos.OrderLoanRequest;
import br.com.ouze.interview.compoundinterest.dtos.OrderLoanResponse;
import br.com.ouze.interview.compoundinterest.entities.LoanEntity;
import br.com.ouze.interview.compoundinterest.entities.LoanInstallmentEntity;
import br.com.ouze.interview.compoundinterest.repositories.LoanInstallmentRepository;
import br.com.ouze.interview.compoundinterest.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class OrderLoan {

    private final LoanRepository loanRepository;

    private final LoanInstallmentRepository loanInstallmentRepository;

    public OrderLoanResponse execute(final OrderLoanRequest orderLoanRequest) {
        final var loan = new LoanEntity(
                orderLoanRequest.cpf(),
                orderLoanRequest.isOuze(),
                orderLoanRequest.installments(),
                orderLoanRequest.value()
        );
        final var loanSaved = this.loanRepository.save(loan);
        this.createInstallments(loanSaved);
        return new OrderLoanResponse(loanSaved.getCpf(), loanSaved.getTotalInstallments(), loanSaved.getTotalValue());
    }

    private void createInstallments(final LoanEntity loan) {
        final var installments = new ArrayList<LoanInstallmentEntity>();
        final var value = loan.getTotalValue().divide(BigDecimal.valueOf(loan.getTotalInstallments()), RoundingMode.FLOOR);
        for (var i = 1; i <= loan.getTotalInstallments(); i++) {
            installments.add(new LoanInstallmentEntity(i, value, LocalDate.now().plus(i, ChronoUnit.MONTHS), loan));
        }
        this.loanInstallmentRepository.saveAll(installments);
    }
}
