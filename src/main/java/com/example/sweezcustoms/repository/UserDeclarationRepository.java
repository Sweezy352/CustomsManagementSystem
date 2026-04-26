package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.UserDeclaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeclarationRepository extends JpaRepository<UserDeclaration, Long> {
}
