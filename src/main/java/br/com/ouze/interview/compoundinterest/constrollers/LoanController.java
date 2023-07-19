package br.com.ouze.interview.compoundinterest.constrollers;

import br.com.ouze.interview.compoundinterest.dtos.GetLoanInstallmentResponse;
import br.com.ouze.interview.compoundinterest.dtos.OrderLoanRequest;
import br.com.ouze.interview.compoundinterest.dtos.OrderLoanResponse;
import br.com.ouze.interview.compoundinterest.services.GetCustomerInstallments;
import br.com.ouze.interview.compoundinterest.services.OrderLoan;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Validated
@RestController
@RequestMapping("/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final OrderLoan orderLoan;

    private final GetCustomerInstallments getCustomerInstallments;

    @PostMapping("/order")
    public OrderLoanResponse order(@Valid @RequestBody OrderLoanRequest dto) throws ResponseStatusException {
        return this.orderLoan.execute(dto);
    }

    @GetMapping("/installment/{clientId}")
    public Page<GetLoanInstallmentResponse> getInstallments(@PathVariable("clientId") Long clientId, Pageable pageable) throws ResponseStatusException{
        return this.getCustomerInstallments.execute(clientId, pageable);
    }
}
