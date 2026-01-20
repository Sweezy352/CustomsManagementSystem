package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.IndividualDtoRequest;
import com.example.sweezcustoms.dto.response.IndividualDtoResponse;
import com.example.sweezcustoms.dto.view.IndividualDtoView;
import com.example.sweezcustoms.entity.IndividualEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class IndividualMapper {

    @Autowired
    @Lazy
    protected IndividualDocumentMapper individualDocumentMapper;
    @Autowired
    @Lazy
    protected DeclarationMapper declarationMapper;


    abstract IndividualEntity toEntity(IndividualDtoRequest individualDtoRequest);

    @Mapping(target = "individualDocumentDtoViews", source = "individualDocuments")
    @Mapping(target = "declarationDtoViews", expression = "java(declarationMapper.toDtoViewList(individualEntity.getDeclarationEntities()))")
    abstract IndividualDtoResponse toDtoResponse(IndividualEntity individualEntity);

    abstract List<IndividualDtoResponse> toDtoResponseList(List<IndividualEntity> individualEntities);

    abstract IndividualDtoView toDtoView(IndividualEntity individualEntity);

    abstract List<IndividualDtoView> toDtoViewList(List<IndividualEntity> individualEntities);
}
