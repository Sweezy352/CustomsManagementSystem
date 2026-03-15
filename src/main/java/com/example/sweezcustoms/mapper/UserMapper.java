package com.example.sweezcustoms.mapper;

import com.example.sweezcustoms.dto.request.UserDtoRequest;
import com.example.sweezcustoms.dto.response.UserDtoResponse;
import com.example.sweezcustoms.dto.view.UserDtoView;
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
    protected RoleMapper roleMapper;


    @Mapping(target = "roles", expression = "java(roleMapper.mapRole(request.getRoleName()))")
    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "yyyy-MM-dd")
    abstract public UserEntity toEntity(UserDtoRequest request);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapListRoles")
    @Mapping(target = "companyDtoView", expression = "java(companyMapper.toDtoView(userEntity.getCompanyOwn()))")
    abstract public UserDtoResponse toDtoResponse(UserEntity userEntity);

    abstract public List<UserDtoResponse> toDtoResponseList(List<UserEntity> userEntities);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapListRoles")
    abstract public UserDtoView toDtoView(UserEntity userEntity);

    abstract public List<UserDtoView> toDtoViewList(List<UserEntity> userEntities);

    @Named("mapListRoles")
     public List<String> mapListRoles(List<RoleEntity> roles){
        return roles.stream().map(RoleEntity::getRoleName).toList();
    }
}
