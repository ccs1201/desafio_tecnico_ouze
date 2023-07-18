package br.com.ouze.interview.compoundinterest.consumers;

import br.com.ouze.interview.compoundinterest.config.queue.PaymentConfirmationConstant;
import br.com.ouze.interview.compoundinterest.dtos.PaymentConfirmationMessage;
import br.com.ouze.interview.compoundinterest.services.ConfirmPayment;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentConfirmationConsumer {

    private final AmqpTemplate template;

    private final ObjectMapper mapper;

    private final ConfirmPayment confirmPayment;

    private final PaymentConfirmationConstant constant;

    @RabbitListener(queues = "${api.event.payment.confirmation.queue}")
    public void consumer(final Message message) {
        try {
            if (!validateXDeath(message)) this.execute(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    private void execute(Message message) throws IOException {
        final var paymentMessage = this.mapper.readValue(message.getBody(), PaymentConfirmationMessage.class);
        this.confirmPayment.execute(paymentMessage);
    }

    private boolean validateXDeath(final Message message) {
        final var xDeath = message.getMessageProperties().getXDeathHeader();
        if (nonNull(xDeath) && ((Long) xDeath.get(0).get("count")) > 3L) {
            this.sendParkingLot(message);
            return true;
        }
        return false;
    }

    private void sendParkingLot(final Message message) {
        final var empty = "";
        this.template.send(empty, constant.getParkingLot(), message);
    }
}
