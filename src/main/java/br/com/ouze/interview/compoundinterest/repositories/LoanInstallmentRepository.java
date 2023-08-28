package br.com.ouze.interview.compoundinterest.repositories;

import br.com.ouze.interview.compoundinterest.entities.LoanInstallment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long> {
    Collection<LoanInstallment> findByLoan_IdAndWasPayedFalse(Long id);
    Optional<LoanInstallment> findByLoan_IdAndInstallment(Long id, Integer installment);
    Page<LoanInstallment> findByLoanId(Long loanId, Pageable pageable);
}
