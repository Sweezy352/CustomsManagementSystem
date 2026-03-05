package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.UserDeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.UserDeclarationDtoResponse;
import com.example.sweezcustoms.dto.view.UserDeclarationDtoView;
import com.example.sweezcustoms.entity.UserDeclaration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class UserDeclarationMapper {
    @Lazy
    @Autowired
    protected DeclarationProductMapper declarationProductMapper;

    public abstract UserDeclaration toEntity(UserDeclarationDtoRequest userDeclarationDtoRequest);
    @Mapping(target = "declarationProductDtos", expression = "java(declarationProductMapper.toDtoResponseList(userDeclaration.getDeclarationProducts()))")
    public abstract UserDeclarationDtoResponse toDtoResponse(UserDeclaration userDeclaration);
    public abstract UserDeclarationDtoView toDtoView(UserDeclaration userDeclaration);
}
