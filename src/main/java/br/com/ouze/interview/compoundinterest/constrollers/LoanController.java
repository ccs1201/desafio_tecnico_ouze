package br.com.ouze.interview.compoundinterest.constrollers;

import br.com.ouze.interview.compoundinterest.dtos.GetLoanInstallmentResponse;
import br.com.ouze.interview.compoundinterest.dtos.OrderLoanRequest;
import br.com.ouze.interview.compoundinterest.dtos.OrderLoanResponse;
import br.com.ouze.interview.compoundinterest.services.GetCustomerInstallments;
import br.com.ouze.interview.compoundinterest.services.OrderLoan;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final OrderLoan orderLoan;

    private final GetCustomerInstallments getCustomerInstallments;

    @PostMapping("/order")
    public OrderLoanResponse order(@RequestBody OrderLoanRequest dto) throws ResponseStatusException {
        return this.orderLoan.execute(dto);
    }

    @GetMapping("/installment/{id}")
    public Page<GetLoanInstallmentResponse> getInstallments(@PathVariable("id") Long clientId, Pageable pageable) throws ResponseStatusException{
        return this.getCustomerInstallments.execute(clientId, pageable);
    }
}
