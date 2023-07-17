package br.com.ouze.interview.compoundinterest.services;

import br.com.ouze.interview.compoundinterest.dtos.GetLoanInstallmentResponse;
import br.com.ouze.interview.compoundinterest.entities.LoanInstallmentEntity;
import br.com.ouze.interview.compoundinterest.repositories.LoanInstallmentRepository;
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

    public Page<GetLoanInstallmentResponse> execute(String cpf, Pageable pageable) {
        final var page = this.loanInstallmentRepository.findByLoanCpf(cpf, pageable);
        return new PageImpl<>(this.buildOutput(page.getContent()), pageable, page.getTotalElements());
    }

    private List<GetLoanInstallmentResponse> buildOutput(List<LoanInstallmentEntity> loanInstallments) {
        return loanInstallments.stream()
                .map(installment -> new GetLoanInstallmentResponse(installment.getInstallment(), installment.getWasPayed(), installment.getValue(), installment.getDueDate()))
                .toList();
    }
}
