package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.DeclarationProductDtoRequest;
import com.example.sweezcustoms.dto.response.DeclarationDtoResponse;
import com.example.sweezcustoms.dto.response.DeclarationProductDtoResponse;
import com.example.sweezcustoms.dto.view.DeclarationProductDtoView;
import com.example.sweezcustoms.entity.DeclarationProductEntity;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DeclarationProductMapper {
    @Autowired
    @Lazy
    protected DeclarationMapper declarationMapper;
    @Autowired
    @Lazy
    protected UserMapper userMapper ;
    @Autowired
    @Lazy
    protected TnvedCodeMapper tnvedCodeMapper;

    abstract DeclarationProductEntity toEntity(DeclarationProductDtoRequest declarationProductDtoRequest);

    @Mappings({
            @Mapping(target = "declarationDtoView", expression = "java(declarationMapper.toDtoView(declarationProductEntity.getDeclarationEntity()))"),
            @Mapping(target = "tnvedCode", expression = "java(tnvedCodeMapper.toDtoView(declarationProductEntity.getTnvedCodeEntity()))"),
            @Mapping(target = "verifiedBy", expression = "java(userMapper.toDtoView(declarationProductEntity.getVerifiedBy()))")
    })
    abstract DeclarationProductDtoResponse toDtoResponse(DeclarationProductEntity declarationProductEntity);

    abstract List<DeclarationProductDtoResponse> toDtoResponseList(List<DeclarationProductEntity> declarationProductEntities);

    abstract DeclarationProductDtoView toDtoView(DeclarationProductEntity declarationProductEntity);

    abstract List<DeclarationProductDtoView> toDtoViewList(List<DeclarationProductEntity> declarationProductEntities);
}
