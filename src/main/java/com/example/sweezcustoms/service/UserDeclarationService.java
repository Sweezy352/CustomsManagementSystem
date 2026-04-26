package com.example.sweezcustoms.service;

import com.example.sweezcustoms.entity.UserDeclaration;

import java.util.List;

public interface UserDeclarationService {
    UserDeclaration createDeclaration(UserDeclaration userDeclaration);
    List<UserDeclaration> getAllDeclarations();
    List<UserDeclaration> getFullUserDeclarations(Long userId);
    UserDeclaration getDeclarationById(Long id);
    UserDeclaration updateDeclaration(UserDeclaration userDeclaration);
    UserDeclaration submitDeclaration(Long id);
    UserDeclaration approveDeclaration(Long id);
    UserDeclaration rejectDeclaration(Long id, String reason);
}
