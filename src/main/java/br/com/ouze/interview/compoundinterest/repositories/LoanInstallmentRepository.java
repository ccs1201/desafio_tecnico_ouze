package br.com.ouze.interview.compoundinterest.repositories;

import br.com.ouze.interview.compoundinterest.entities.LoanInstallmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallmentEntity, Long> {
}
