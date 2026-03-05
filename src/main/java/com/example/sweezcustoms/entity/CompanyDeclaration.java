package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "company_declarations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDeclaration extends DeclarationEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private ContractEntity contractEntity;
    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;
    @Column(name = "invoice_date", nullable = false)
    private String invoiceDate;
    @Column(name = "incoterms_code", nullable = false)
    private String incotermsCode;
    @Column(name = "incoterms_place", nullable = false)
    private String incotermsPlace;
    @Column(name = "transport_type", nullable = false)
    private String transportType;
    @Column(name = "transport_id", nullable = false)
    private String transportId;

}
