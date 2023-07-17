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

@RestController
@RequestMapping("/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final OrderLoan orderLoan;

    private final GetCustomerInstallments getCustomerInstallments;

    @PostMapping("/order")
    public OrderLoanResponse order(@RequestBody OrderLoanRequest dto) {
        return this.orderLoan.execute(dto);
    }

    @GetMapping("/installment/{cpf}")
    public Page<GetLoanInstallmentResponse> getInstallments(@PathVariable("cpf") String cpf, Pageable pageable) {
        return this.getCustomerInstallments.execute(cpf, pageable);
    }
}
