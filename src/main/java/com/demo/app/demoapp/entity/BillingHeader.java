package com.demo.app.demoapp.entity;

import jakarta.persistence.*;
import lombok.*;

//@Entity
//@Data
//public class BillingHeader {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String billingId;
//    private String invoiceDate;
//    private Double totalInvoiceAmount;
//
//
//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "payment_information_id", referencedColumnName = "id")
//    private PaymentInformation paymentInformation;
//
//}

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "billing_id")
    private String billingId;

    @Column(name = "invoice_date")
    private String invoiceDate;

    @Column(name = "total_invoice_amount")
    private Double totalInvoiceAmount;
}


