package com.demo.app.demoapp.dto;


import com.demo.app.demoapp.entity.BillingLineInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingLineDto {

    @JsonProperty("billing_line_information")
    private BillingLineInformationDto billingLineInformation;
}
