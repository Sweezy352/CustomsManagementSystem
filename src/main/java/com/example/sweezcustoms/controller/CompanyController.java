package com.example.sweezcustoms.controller;

import com.example.sweezcustoms.dto.request.CompanyDtoRequest;
import com.example.sweezcustoms.dto.response.CompanyDtoResponse;
import com.example.sweezcustoms.dto.view.CompanyDtoView;
import com.example.sweezcustoms.dto.view.UserDtoView;
import com.example.sweezcustoms.mapper.CompanyMapper;
import com.example.sweezcustoms.mapper.UserMapper;
import com.example.sweezcustoms.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    @PostMapping("/create-company")
    public ResponseEntity<CompanyDtoResponse> createCompany(@Valid @RequestBody CompanyDtoRequest companyDtoRequest){
        System.out.println(companyDtoRequest.getAddress());
        return ResponseEntity.ok(companyMapper.toDtoResponse(companyService.createCompany(companyMapper.toEntity(companyDtoRequest))));
    }


    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("@spEL.canAccessCompany(#id, 'id')")
    public ResponseEntity<CompanyDtoResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(companyMapper.toDtoResponse(companyService.getCompanyById(id)));
    }

    @GetMapping("/get-by-company-name")
    public ResponseEntity<CompanyDtoView> getCompanyByName(@RequestParam String companyName){
        return ResponseEntity.ok(companyMapper.toDtoView(companyService.getByCompanyName(companyName)));
    }

    @GetMapping("/get-employees-company/{id}")
    @PreAuthorize("@spEL.canAccessCompany(#id, 'id')")
    public ResponseEntity<List<UserDtoView>> getEmployeesCompany(Long id){
        return ResponseEntity.ok(userMapper.toDtoViewList(companyService.getEmployees(id)));
    }

    @GetMapping("/get-by-tin")
    public ResponseEntity<CompanyDtoView> getCompanyByTin(@RequestParam String companyTin){
        return ResponseEntity.ok(companyMapper.toDtoView(companyService.getCompanyByTin(companyTin)));
    }

    @GetMapping("/get-by-okpo")
    public ResponseEntity<CompanyDtoView> getCompanyByOkpo(@RequestParam String companyOkpo){
        return ResponseEntity.ok(companyMapper.toDtoView(companyService.getCompanyByOkpo(companyOkpo)));
    }

    @GetMapping("/get-by-customs-code")
    public ResponseEntity<CompanyDtoView> getCompanyByCustomsCode(@RequestParam String companyCustomsCode){
        return ResponseEntity.ok(companyMapper.toDtoView(companyService.getCompanyByCustomsCode(companyCustomsCode)));
    }
}
