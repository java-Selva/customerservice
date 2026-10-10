package com.microservice.customerservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerKafkaProducer {

    private final KafkaTemplate<String, CustomerEvent>  kafkaTemplate;
    private static final String TOPIC = "customer.event";

    public void publishCustomerCreated(Long customerId, String email)
    {
        CustomerEvent event = CustomerEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("CUSTOMER_CREATED")
                .customerId(customerId)
                .email(email)
                .eventTime(LocalDateTime.now())
                .build();

        kafkaTemplate.send(TOPIC,customerId.toString(),event);
    }

}
