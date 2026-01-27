package com.example.sweezcustoms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "branches_company")
public class BranchCompanyEntity extends BaseEntity{
    @Column(name = "branch_name", nullable = false)
    private String branchName;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    private CompanyEntity companyEntity;
}
