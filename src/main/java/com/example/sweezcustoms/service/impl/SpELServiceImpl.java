package com.example.sweezcustoms.service.impl;

import com.example.sweezcustoms.entity.CompanyEntity;
import com.example.sweezcustoms.entity.UserEntity;
import com.example.sweezcustoms.service.AuthService;
import com.example.sweezcustoms.service.SpELService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@Service("spEL")
@RequiredArgsConstructor
public class SpELServiceImpl implements SpELService {
    private final AuthService authService;

    private static final List<String> FULL_ACCESS_ROLES = List.of("ADMIN", "INSPECTOR");

    @Override
    public boolean canAccessCompany(Object value, String fieldName) {
        UserEntity currentUser = (UserEntity) authService.getCurrent();

        // ADMIN и INSPECTOR имеют доступ ко всем компаниям
        boolean isPrivileged = currentUser.getRoles().stream()
                .anyMatch(r -> FULL_ACCESS_ROLES.contains(r.getRoleName()));
        if (isPrivileged) return true;

        if (Objects.isNull(value)) return false;

        // Владелец — companyOwn, сотрудник — companyEntity
        CompanyEntity company = currentUser.getCompanyOwn() != null
                ? currentUser.getCompanyOwn()
                : currentUser.getCompanyEntity();

        if (Objects.isNull(company)) return false;

        try {
            // Ищем поле по иерархии классов (id находится в BaseEntity)
            Field field = findField(CompanyEntity.class, fieldName);
            if (field == null) return false;
            field.setAccessible(true);
            Object fieldValue = field.get(company);
            field.setAccessible(false);
            return value.toString().equals(fieldValue.toString());
        } catch (Exception ex) {
            return false;
        }
    }

    // Ищет поле в классе и всех его суперклассах
    private Field findField(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
}
