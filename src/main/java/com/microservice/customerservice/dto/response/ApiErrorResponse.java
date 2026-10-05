package com.microservice.customerservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> validationErrors;
}
