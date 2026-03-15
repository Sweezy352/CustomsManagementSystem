package com.example.sweezcustoms.dto.view;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoView {
    private Long id;
    private String mail;
    private String fullName;
    private String phone;
    private List<String> roles;
}
