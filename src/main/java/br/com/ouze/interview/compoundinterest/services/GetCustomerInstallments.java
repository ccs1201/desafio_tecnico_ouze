package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.dtos.GetLoanInstallmentResponse;
import br.com.ouze.interview.compoundinterest.entities.LoanInstallment;
import br.com.ouze.interview.compoundinterest.exceptions.NotFoundException;
import br.com.ouze.interview.compoundinterest.repositories.LoanInstallmentRepository;
import br.com.ouze.interview.compoundinterest.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCustomerInstallments {

    private final LoanInstallmentRepository loanInstallmentRepository;

    private final LoanRepository loanRepository;

    public Page<GetLoanInstallmentResponse> execute(Long clientId, Pageable pageable) {
        final var loan = this.loanRepository.findFirstByClientIdOrderByCreateAtDesc(clientId)
                .orElseThrow(() -> new NotFoundException(String.format("Empréstimo não encontrado para o cliente %d", clientId)));
        final var page = this.loanInstallmentRepository.findByLoanId(loan.getId(), pageable);
        return new PageImpl<>(this.buildOutput(page.getContent()), pageable, page.getTotalElements());
    }

    private List<GetLoanInstallmentResponse> buildOutput(List<LoanInstallment> loanInstallments) {
        return loanInstallments.stream()
                .map(installment -> new GetLoanInstallmentResponse(installment.getInstallment(), installment.getWasPayed(), installment.getValue(), installment.getDueDate()))
                .toList();
    }
}
