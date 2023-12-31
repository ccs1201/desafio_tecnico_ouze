package br.com.ouze.interview.compoundinterest.repositories;

import br.com.ouze.interview.compoundinterest.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findFirstByClientIdAndIsActiveTrue(@NonNull Long clientId);
    Optional<Loan> findFirstByClientIdOrderByCreateAtDesc(Long clientId);
}
