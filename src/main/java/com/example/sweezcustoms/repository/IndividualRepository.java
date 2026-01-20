package com.example.sweezcustoms.repository;

import com.example.sweezcustoms.entity.IndividualEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualRepository extends JpaRepository<IndividualEntity, Long> {
    Optional<IndividualEntity> findByTin(String tin);
    Optional<IndividualEntity> findByPassportSeries(String passportSeries);
    Optional<IndividualEntity> findByFullName(String fullName);

}
