package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_declarations")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeclaration extends DeclarationEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    @Column(name = "arrival_way")
    private String arrivalWay;
    @Column(name = "tracking_number")
    private String trackingNumber;
    @Column(name = "is_personal", nullable = false)
    private boolean isPersonal;
}
