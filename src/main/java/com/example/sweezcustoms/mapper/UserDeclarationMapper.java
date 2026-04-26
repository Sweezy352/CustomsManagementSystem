package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.UserDeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.UserDeclarationDtoResponse;
import com.example.sweezcustoms.dto.view.UserDeclarationDtoView;
import com.example.sweezcustoms.entity.UserDeclaration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserDeclarationMapper {
    @Lazy
    @Autowired
    protected DeclarationProductMapper declarationProductMapper;

    @Mapping(target = "currency", expression = "java(userDeclarationDtoRequest.getCurrency() != null ? com.example.sweezcustoms.enums.CurrencyEnum.valueOf(userDeclarationDtoRequest.getCurrency()) : null)")
    public abstract UserDeclaration toEntity(UserDeclarationDtoRequest userDeclarationDtoRequest);

    @Mapping(target = "declarationProductDtos", expression = "java(declarationProductMapper.toDtoResponseList(userDeclaration.getDeclarationProducts()))")
    @Mapping(target = "currency", expression = "java(userDeclaration.getCurrency() != null ? userDeclaration.getCurrency().name() : null)")
    public abstract UserDeclarationDtoResponse toDtoResponse(UserDeclaration userDeclaration);
    public abstract List<UserDeclarationDtoResponse> toDtoResponseList(List<UserDeclaration> userDeclarations);
    public abstract UserDeclarationDtoView toDtoView(UserDeclaration userDeclaration);
}
