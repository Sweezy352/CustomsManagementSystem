package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.TnvedCodeDtoRequest;
import com.example.sweezcustoms.dto.response.TnvedCodeDtoResponse;
import com.example.sweezcustoms.dto.view.TnvedCodeDtoView;
import com.example.sweezcustoms.entity.TnvedCodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TnvedCodeMapper {
    @Autowired
    @Lazy
    protected UserMapper userMapper;

    public abstract TnvedCodeEntity toEntity(TnvedCodeDtoRequest tnvedCodeDtoRequest);

    @Mapping(target = "userDtoView", expression = "java(userMapper.toDtoView(tnvedCodeEntity.getUserEntity()))")
    public abstract TnvedCodeDtoResponse toDtoResponse(TnvedCodeEntity tnvedCodeEntity);

    public abstract List<TnvedCodeDtoResponse> toDtoResponseList(List<TnvedCodeEntity> tnvedCodeEntities);

    public abstract TnvedCodeDtoView toDtoView(TnvedCodeEntity tnvedCodeEntity);

    public abstract List<TnvedCodeDtoView> toDtoViewList(List<TnvedCodeEntity> tnvedCodeEntities);
}
