package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.TnvedCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TnvedCodeRepository extends JpaRepository<TnvedCodeEntity, Long> {
    Optional<TnvedCodeEntity> findByCode(String code);
}
