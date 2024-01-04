package com.sparta.adminserver.admin.dto;

import com.sparta.adminserver.admin.entity.Admin;
import com.sparta.adminserver.admin.entity.AdminRoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private Long id;
    private String email;
    private String password;
    private String department; // 부서 (curriculum / marketing / development)

    @Enumerated(value = EnumType.STRING)
    private AdminRoleEnum role; // 권한 (MANAGER, STAFF)

    public SignupResponseDto(Admin saveAdmin) {
        this.id = saveAdmin.getId();
        this.email = saveAdmin.getEmail();
        this.password = saveAdmin.getPassword();
        this.department = saveAdmin.getDepartment();
        this.role = saveAdmin.getRole();
    }
}
