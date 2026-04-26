package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.CarDeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.CarDeclarationDtoResponse;
import com.example.sweezcustoms.entity.CarDeclaration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CarDeclarationMapper {

    @Mapping(target = "ndsAmount", ignore = true)
    @Mapping(target = "customsDutyAmount", ignore = true)
    @Mapping(target = "exciseAmount", ignore = true)
    @Mapping(target = "currency", expression = "java(request.getCurrency() != null ? com.example.sweezcustoms.enums.CurrencyEnum.valueOf(request.getCurrency()) : null)")
    @Mapping(target = "documentType", ignore = true)
    @Mapping(target = "declarationProducts", ignore = true)
    @Mapping(target = "fileName", ignore = true)
    public abstract CarDeclaration toEntity(CarDeclarationDtoRequest request);

    @Mapping(target = "declarationType", expression = "java(carDeclaration.getCurrency() != null ? carDeclaration.getCurrency().name() : null)")
    @Mapping(target = "nds", source = "ndsAmount")
    @Mapping(target = "customsDuty", source = "customsDutyAmount")
    @Mapping(target = "excise", source = "exciseAmount")
    public abstract CarDeclarationDtoResponse toDtoResponse(CarDeclaration carDeclaration);

    public abstract List<CarDeclarationDtoResponse> toDtoResponseList(List<CarDeclaration> carDeclarations);
}
