package br.com.ouze.interview.compoundinterest.constrollers;

import br.com.ouze.interview.compoundinterest.dtos.OrderLoanRequest;
import br.com.ouze.interview.compoundinterest.dtos.OrderLoanResponse;
import br.com.ouze.interview.compoundinterest.services.OrderLoan;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final OrderLoan orderLoan;

    @PostMapping("/order")
    public OrderLoanResponse order(@RequestBody OrderLoanRequest dto) {
        return this.orderLoan.execute(dto);
    }
}
