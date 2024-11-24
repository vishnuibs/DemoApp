package com.demo.app.demoapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InputRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BillingHeader billingHeader;

    @OneToMany(mappedBy = "inputRequest", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<BillingLineInformation> billingLines;

}
