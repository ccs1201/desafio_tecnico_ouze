package br.com.ouze.interview.compoundinterest.consumer;

import br.com.ouze.interview.compoundinterest.dtos.PaymentDTO;
import br.com.ouze.interview.compoundinterest.dtos.PaymentErrorDTO;
import br.com.ouze.interview.compoundinterest.exceptions.NotFoundException;
import br.com.ouze.interview.compoundinterest.publisher.PaymentProducer;
import br.com.ouze.interview.compoundinterest.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;
    private final PaymentProducer paymentProducer;

    @RabbitListener(queues = {"${api.event.payment.confirmation.queue}"})
    public void received(@Payload PaymentDTO paymentDTO, @Header("times") Integer times) {

        log.info("Recebido: {} Times {}", paymentDTO, times);
        boolean hasError = false;
        Exception ex = null;

        try {
            paymentService.payInstallment(paymentDTO);
        } catch (NotFoundException e) {
            log.error("Erro ao processar pagamento {} Times: {}", paymentDTO, times, e);
        } catch (Exception e) {
            hasError = true;
            log.error("Erro ao processar pagamento {} Times: {}", paymentDTO, times);
            ex = e;
        }
        if (hasError && times < 3) {
            paymentProducer.publish(paymentDTO, ++times);
        } else if (times >= 3) {
            paymentProducer.publishToParkinLot(new PaymentErrorDTO(paymentDTO, ex));
        }
    }
}
