package com.demo.app.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillingLineInformationDto {

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("line_type")
    private String lineType;
}
