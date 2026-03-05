package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.IndividualDocumentDtoRequest;
import com.example.sweezcustoms.dto.response.IndividualDocumentDtoResponse;
import com.example.sweezcustoms.dto.view.IndividualDocumentDtoView;
import com.example.sweezcustoms.entity.IndividualDocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class IndividualDocumentMapper {
    @Autowired
    @Lazy
    protected UserMapper userMapper;


    abstract IndividualDocumentEntity toEntity(IndividualDocumentDtoRequest individualDocumentDtoRequest);

    @Mapping(target = "userDtoView", expression = "java(userMapper.toDtoView(individualDocumentEntity.getUserEntity()))")
    @Mapping(target = "verifiedBy", expression = "java(userMapper.toDtoView(individualDocumentEntity.getVerifiedBy()))")
    abstract IndividualDocumentDtoResponse toDtoResponse(IndividualDocumentEntity individualDocumentEntity);

    abstract List<IndividualDocumentDtoResponse> toDtoResponseList(List<IndividualDocumentEntity> individualDocumentEntities);

    abstract IndividualDocumentDtoView toDtoView(IndividualDocumentEntity individualDocumentEntity);

    abstract List<IndividualDocumentDtoView> toDtoViewList(List<IndividualDocumentDtoView> individualDocumentDtoViews);
}
