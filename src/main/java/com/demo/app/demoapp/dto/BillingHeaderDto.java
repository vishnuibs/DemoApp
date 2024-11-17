package com.demo.app.demoapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class BillingHeaderDto {

    @JsonProperty("billing_id")
    private String billingId;

    @JsonProperty("invoice_date")
    private String invoiceDate;

    @JsonProperty("total_invoice_amount")
    private Double totalInvoiceAmount;
}
