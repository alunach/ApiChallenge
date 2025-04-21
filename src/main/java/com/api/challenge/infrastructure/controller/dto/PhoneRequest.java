package com.api.challenge.infrastructure.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PhoneRequest {
    private String number;
    private String cityCode;
    private String countryCode;
}
