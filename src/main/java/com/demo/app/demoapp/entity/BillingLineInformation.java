package com.demo.app.demoapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingLineInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "input_request_id", referencedColumnName = "id")
    @JsonIgnore
    private InputRequest inputRequest;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "line_type")
    private String lineType;

}
