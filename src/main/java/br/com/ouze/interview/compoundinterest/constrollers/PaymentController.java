package br.com.ouze.interview.compoundinterest.constrollers;

import br.com.ouze.interview.compoundinterest.dtos.PaymentDTO;
import br.com.ouze.interview.compoundinterest.publisher.PaymentProducer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/loan/{loan_id}/installment/{installment_number}/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentProducer publisher;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<Void> pay(@PathVariable @NotNull @Positive Long loan_id, @PathVariable @Positive @NotNull Integer installment_number) {
        return CompletableFuture.runAsync(() ->
                publisher.publish(new PaymentDTO(loan_id, installment_number)));
    }
}
