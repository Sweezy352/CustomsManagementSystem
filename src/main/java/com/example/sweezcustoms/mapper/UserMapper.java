package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.UserDtoRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.dto.view.ParticipantDto;
import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.entity.IndividualEntity;
import com.example.sweezcustoms.entity.RoleEntity;
import com.example.sweezcustoms.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    @Lazy
    protected CompanyMapper companyMapper;
    @Autowired
    @Lazy
    protected IndividualMapper individualMapper;

    abstract UserEntity toEntity(UserDtoRequest request);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapListRoles")
    @Mapping(target = "participantDto", expression = "java(ParticipantMapper.toParticipantDto(userEntity.getParticipant()))")
    abstract UserDtoResponse toDtoResponse(UserEntity userEntity);

    abstract List<UserDtoResponse> toDtoResponseList(List<UserEntity> userEntities);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapListRoles")
    abstract UserDtoView toDtoView(UserEntity userEntity);

    abstract List<UserDtoView> toDtoViewList(List<UserEntity> userEntities);

    @Named("mapListRoles")
     public List<String> mapListRoles(List<RoleEntity> roles){
        return roles.stream().map(RoleEntity::getRoleName).toList();
    }
}
