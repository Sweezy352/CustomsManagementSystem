package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.DeclarationProductDocumentDtoRequest;
import com.example.sweezcustoms.dto.response.DeclarationProductDocumentDtoResponse;
import com.example.sweezcustoms.entity.DeclarationProductDocumentEntity;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DeclarationProductDocumentMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;

    abstract DeclarationProductDocumentEntity toEntity(DeclarationProductDocumentDtoRequest declarationProductDocumentDtoRequest);

    @Mapping(target = "verifiedBy", expression = "java(userMapper.toDtoView(declarationProductDocumentEntity.getVerifiedBy()))")
    abstract DeclarationProductDocumentDtoResponse toDtoResponse(DeclarationProductDocumentEntity declarationProductDocumentEntity);

    abstract List<DeclarationProductDocumentDtoResponse> toDtoResponseList(List<DeclarationProductDocumentEntity> declarationProductDocumentEntities);

}
