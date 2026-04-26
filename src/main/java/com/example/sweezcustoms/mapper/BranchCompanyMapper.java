package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.view.BranchCompanyDtoView;
import com.example.sweezcustoms.entity.BranchCompanyEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BranchCompanyMapper {
    public abstract BranchCompanyDtoView toDtoView(BranchCompanyEntity branchCompanyEntity);
    public abstract List<BranchCompanyDtoView> toDtoViewList(List<BranchCompanyEntity> branchCompanyEntities);
    public abstract BranchCompanyEntity toEntity(BranchCompanyDtoView branchCompanyDtoView);
}
