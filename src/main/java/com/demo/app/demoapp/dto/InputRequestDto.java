package com.demo.app.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
public class InputRequestDto {

    @JsonProperty("billing_header")
    private BillingHeaderDto billingHeader;

    @JsonProperty("billing_lines")
    private List<BillingLineDto> billingLines;

}
