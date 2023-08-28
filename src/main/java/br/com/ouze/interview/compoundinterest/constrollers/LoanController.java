package br.com.ouze.interview.compoundinterest.constrollers;

import br.com.ouze.interview.compoundinterest.dtos.GetLoanInstallmentResponse;
import br.com.ouze.interview.compoundinterest.dtos.LoanInput;
import br.com.ouze.interview.compoundinterest.dtos.LoanResponse;
import br.com.ouze.interview.compoundinterest.services.GetCustomerInstallments;
import br.com.ouze.interview.compoundinterest.services.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

@Validated
@RestController
@RequestMapping("/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final GetCustomerInstallments getCustomerInstallments;
    private final LoanService service;

    @GetMapping("/installment/{clientId}")
    public Page<GetLoanInstallmentResponse> getInstallments(@PathVariable("clientId") Long clientId, Pageable pageable) throws ResponseStatusException {
        return this.getCustomerInstallments.execute(clientId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<LoanResponse> createLan(@RequestBody @Valid LoanInput input) {

        return CompletableFuture.supplyAsync(() ->
                service.createLoan(input.toLoan())
        ).thenApply(LoanResponse::new);
    }
}
