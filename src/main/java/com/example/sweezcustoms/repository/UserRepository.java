package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByMail(String mail);
    Optional<UserEntity> findByPhone(String phone);

    Optional<UserEntity> findByUsernameOrMail(String username, String mail);

}
