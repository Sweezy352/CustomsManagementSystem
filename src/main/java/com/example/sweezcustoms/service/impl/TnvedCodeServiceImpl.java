package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.TnvedCodeEntity;
import com.example.sweezcustoms.exceptions.TnvedCodeNotFoundException;
import com.example.sweezcustoms.repository.TnvedCodeRepository;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.TnvedCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TnvedCodeServiceImpl implements TnvedCodeService {
    private final TnvedCodeRepository tnvedCodeRepository;
    private final AuthService authService;

    @Override
    public TnvedCodeEntity create(TnvedCodeEntity tnvedCodeEntity) {
        tnvedCodeEntity.setUserEntity(authService.getCurrent());
        return tnvedCodeRepository.save(tnvedCodeEntity);
    }

    @Override
    public TnvedCodeEntity getById(Long id) {
        return tnvedCodeRepository.findById(id)
                .orElseThrow(() -> new TnvedCodeNotFoundException("tnved.code.not.found"));
    }

    @Override
    public TnvedCodeEntity getByCode(String code) {
        return tnvedCodeRepository.findByCode(code)
                .orElseThrow(() -> new TnvedCodeNotFoundException("tnved.code.not.found"));
    }

    @Override
    public List<TnvedCodeEntity> getAll() {
        return tnvedCodeRepository.findAll();
    }

    @Override
    public TnvedCodeEntity update(Long id, TnvedCodeEntity tnvedCodeEntity) {
        TnvedCodeEntity existing = getById(id);
        existing.setCode(tnvedCodeEntity.getCode());
        existing.setDescription(tnvedCodeEntity.getDescription());
        existing.setDefaultCustomsDutyRate(tnvedCodeEntity.getDefaultCustomsDutyRate());
        existing.setDefaultExciseRate(tnvedCodeEntity.getDefaultExciseRate());
        existing.setDefaultNdsRate(tnvedCodeEntity.getDefaultNdsRate());
        return tnvedCodeRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        tnvedCodeRepository.deleteById(id);
    }
}
