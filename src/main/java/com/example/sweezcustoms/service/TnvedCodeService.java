package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.TnvedCodeEntity;

import java.util.List;

public interface TnvedCodeService {
    TnvedCodeEntity create(TnvedCodeEntity tnvedCodeEntity);
    TnvedCodeEntity getById(Long id);
    TnvedCodeEntity getByCode(String code);
    List<TnvedCodeEntity> getAll();
    TnvedCodeEntity update(Long id, TnvedCodeEntity tnvedCodeEntity);
    void delete(Long id);
}
