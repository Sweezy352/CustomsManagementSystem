package com.example.sweezcustoms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity implements UserDetails {
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "pin", nullable = false, unique = true)
    private String pin;
    @Column(name = "passport_number", nullable = false, unique = true)
    private String passportNumber;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "m2m_users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private CompanyEntity companyEntity;

    @OneToOne
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    private Participant participant;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDate.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return mail;
    }


}
