package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.DeclarationDtoRequest;
import com.example.sweezcustoms.dto.response.DeclarationDtoResponse;
import com.example.sweezcustoms.dto.view.DeclarationDtoView;
import com.example.sweezcustoms.dto.view.ParticipantDto;
import com.example.sweezcustoms.entity.DeclarationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class DeclarationMapper {

    @Autowired
    @Lazy
    protected UserMapper userMapper;
    @Autowired
    @Lazy
    protected IndividualMapper individualMapper;
    @Autowired
    @Lazy
    protected CompanyMapper companyMapper;
    @Autowired
    @Lazy
    protected DeclarationProductMapper declarationProductMapper;

    abstract DeclarationEntity toEntity(DeclarationDtoRequest declarationDtoRequest);

    @Mapping(target = "participantDto", expression = "java(ParticipantMapper.toParticipantDto(declarationEntity.getParticipant()))")
    @Mapping(target = "verifiedBy", expression = "java(userMapper.toDtoView(declarationEntity.getVerifiedBy()))")
    @Mapping(target = "declarationProductDtoViews", expression = "java(declarationProductMapper.toDtoViewList(declarationEntity.getDeclarationProducts()))")
    abstract DeclarationDtoResponse toDtoResponse(DeclarationEntity declarationEntity);

    abstract List<DeclarationDtoResponse> toDtoResponseList(List<DeclarationEntity> declarationEntities);

    abstract DeclarationDtoView toDtoView(DeclarationEntity declarationEntity);

    abstract List<DeclarationDtoView> toDtoViewList(List<DeclarationEntity> declarationEntities);


}
