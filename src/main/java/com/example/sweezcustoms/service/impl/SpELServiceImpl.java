package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.SpELService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Objects;

@Service("spEL")
@RequiredArgsConstructor
public class SpELServiceImpl implements SpELService {
    private final AuthService authService;

    @Override
    public boolean canAccessCompany(Object value, String fieldName) {
        UserEntity currentUser = (UserEntity) authService.getCurrent();
        CompanyEntity companyEntity = currentUser.getCompanyEntity();
        if(Objects.isNull(companyEntity) || Objects.isNull(value)) return false;

        try{
            Field field = CompanyEntity.class.getField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(companyEntity);
            field.setAccessible(false);
            return value.toString().equals(fieldValue.toString());

        }catch (Exception ex){
            return false;
        }
    }
}
