package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_declarations")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDeclaration extends DeclarationEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private ContractEntity contractEntity;
    @Column(name = "invoice_number")
    private String invoiceNumber;
    @Column(name = "invoice_date")
    private String invoiceDate;
    @Column(name = "incoterms_code")
    private String incotermsCode;
    @Column(name = "incoterms_place")
    private String incotermsPlace;
    @Column(name = "transport_type")
    private String transportType;
    @Column(name = "transport_id")
    private String transportId;

    @PrePersist
    public void prePersist(){
        status = CustomsStatusEnum.DRAFT;
        createdAt = LocalDateTime.now();
    }

}
