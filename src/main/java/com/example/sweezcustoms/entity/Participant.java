package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Participant extends BaseEntity{
    protected String address;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "participant")
    private UserEntity owner;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participant")
    private List<PaymentInvoiceEntity> paymentInvoices;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participant")
    private List<DeclarationEntity> declarationEntities;

    abstract public String getType();
    abstract public String getTin();
    abstract public CustomsStatusEnum getStatus();
}
