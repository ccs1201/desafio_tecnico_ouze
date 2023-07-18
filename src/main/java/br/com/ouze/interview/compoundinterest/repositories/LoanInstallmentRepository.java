package br.com.ouze.interview.compoundinterest.repositories;

import br.com.ouze.interview.compoundinterest.entities.LoanInstallmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallmentEntity, Long> {
    Page<LoanInstallmentEntity> findByLoanCpf(String cpf, Pageable pageable);

    List<LoanInstallmentEntity> findByLoanIdAndWasPayedFalse(Long loanId);
}
