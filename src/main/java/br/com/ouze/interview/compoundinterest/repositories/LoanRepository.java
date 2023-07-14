package br.com.ouze.interview.compoundinterest.repositories;

import br.com.ouze.interview.compoundinterest.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
}
