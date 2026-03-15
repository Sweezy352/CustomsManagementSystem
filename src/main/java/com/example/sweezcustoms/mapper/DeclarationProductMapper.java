package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.DeclarationProductDtoRequest;
import com.example.sweezcustoms.dto.response.DeclarationProductDto;
import com.example.sweezcustoms.entity.DeclarationProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DeclarationProductMapper {
    @Mapping(target = "tnvedCode", ignore = true)
    public abstract DeclarationProduct toEntity(DeclarationProductDtoRequest declarationProductDtoRequest);
    @Mapping(target = "tnvedCode", ignore = true)
    public abstract DeclarationProductDto toDtoResponse(DeclarationProduct declarationProduct);
    public abstract List<DeclarationProductDto> toDtoResponseList(List<DeclarationProduct> declarationProducts);
}
