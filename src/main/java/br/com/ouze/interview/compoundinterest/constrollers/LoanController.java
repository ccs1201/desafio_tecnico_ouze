package br.com.ouze.interview.compoundinterest.constrollers;

import br.com.ouze.interview.compoundinterest.dtos.GetLoanInstallmentResponse;
import br.com.ouze.interview.compoundinterest.services.GetCustomerInstallments;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Validated
@RestController
@RequestMapping("/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final GetCustomerInstallments getCustomerInstallments;

    @GetMapping("/installment/{clientId}")
    public Page<GetLoanInstallmentResponse> getInstallments(@PathVariable("clientId") Long clientId, Pageable pageable) throws ResponseStatusException{
        return this.getCustomerInstallments.execute(clientId, pageable);
    }
}
