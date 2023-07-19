package br.com.ouze.interview.compoundinterest.repositories;

import br.com.ouze.interview.compoundinterest.entities.LoanInstallment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {
    Page<LoanInstallment> findByLoanId(Long loanId, Pageable pageable);

    List<LoanInstallment> findByLoanIdAndWasPayedFalse(Long loanId);
}
