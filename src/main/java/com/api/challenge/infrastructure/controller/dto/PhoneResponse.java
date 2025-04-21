package com.api.challenge.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PhoneResponse {
    private String number;
    private String citycode;
    private String contrycode;
}
