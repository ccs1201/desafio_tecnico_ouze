package br.com.ouze.interview.compoundinterest.config.queue;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PaymentConfirmationConstant {

    @Value("${api.event.exchange}")
    private String exchange;

    @Value("${api.event.payment.confirmation.routing-key}")
    private String routingKey;

    @Value("${api.event.payment.confirmation.queue}")
    private String queue;

    @Value("${api.event.payment.confirmation.error.ttl}")
    private Integer ttl;

    public static final String EMPTY = "";

    public String getDql() {
        return this.queue.concat(".dlq");
    }

    public String getParkingLot() {
        return this.queue.concat(".parking-lot");
    }
}
