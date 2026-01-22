package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.entity.RoleEntity;
import com.example.sweezcustoms.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleMapper {
    private final RoleRepository roleRepository;

    public List<RoleEntity> mapRole(String roleName){
        return List.of(roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found")));
    }
}
