package com.microservice.customerservice.kafka;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEvent {
    private String eventId;
    private String eventType;
    private Long customerId;
    private String email;
    private LocalDateTime eventTime;
}
