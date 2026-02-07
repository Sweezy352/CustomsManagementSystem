package com.example.sweezcustoms.entity;

import com.example.sweezcustoms.enums.CustomsStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "individuals")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IndividualEntity extends Participant{
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "passport_series", nullable = false, unique = true)
    private String passportSeries;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "tin", nullable = false, unique = true)
    private String tin;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "status")
    private CustomsStatusEnum status;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verified_by", referencedColumnName = "id")
    private UserEntity verifiedBy;
    @Column(name = "verifiedAt")
    private LocalDateTime verifiedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "individualEntity")
    private List<IndividualDocumentEntity> individualDocuments;

    @PrePersist
    public void prePersist() {
        status = CustomsStatusEnum.PENDING;
        createdAt = LocalDateTime.now();
    }

    @Override
    public String getType() {
        return fullName;
    }

    @Override
    public String getTin() {
        return tin;
    }

    @Override
    public CustomsStatusEnum getStatus() {
        return status;
    }
}
