package com.sparta.adminserver.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String department; // 부서 (curriculum / marketing / development)

    @Enumerated(value = EnumType.STRING)
    private AdminRoleEnum role; // 권한 (MANAGER, STAFF)

    public Admin(String email, String password, String department, AdminRoleEnum role) {
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }
}
