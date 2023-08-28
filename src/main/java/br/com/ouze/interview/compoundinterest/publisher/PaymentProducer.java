package br.com.ouze.interview.compoundinterest.publisher;

import br.com.ouze.interview.compoundinterest.configs.queue.PaymentConfirmationConstant;
import br.com.ouze.interview.compoundinterest.dtos.PaymentDTO;
import br.com.ouze.interview.compoundinterest.dtos.PaymentErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    private final PaymentConfirmationConstant constant;

    public void publish(PaymentDTO paymentDTO, Integer times) {
        log.info("Publicando: {}", paymentDTO);
        rabbitTemplate.convertAndSend(queue.getName(), paymentDTO,
                m -> {
                    m.getMessageProperties().setHeader("times", times);
                    return m;
                });
    }

    public void publish(PaymentDTO paymentDTO) {
        publish(paymentDTO, 1);
    }

    public void publishToParkinLot(PaymentErrorDTO paymentErrorDTO) {
        log.info("Enviando a parkin lot: {}", paymentErrorDTO);
        rabbitTemplate.convertAndSend(constant.getParkingLot(), paymentErrorDTO);
    }
}
