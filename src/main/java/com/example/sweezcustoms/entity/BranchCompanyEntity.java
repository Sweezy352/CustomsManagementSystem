package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "branches_company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BranchCompanyEntity extends BaseEntity{
    @Column(name = "branch_name", nullable = false)
    private String branchName;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @ManyToOne()
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;
}
