package com.sparta.adminserver.admin.service;

import com.sparta.adminserver.admin.dto.SignupRequestDto;
import com.sparta.adminserver.admin.dto.SignupResponseDto;
import com.sparta.adminserver.admin.entity.Admin;
import com.sparta.adminserver.admin.entity.AdminRoleEnum;
import com.sparta.adminserver.admin.jwt.JwtUtil;
import com.sparta.adminserver.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        String department = requestDto.getDepartment();
        if (!(department.equals("curriculum") || department.equals("marketing") || department.equals("development"))) {
            throw new IllegalArgumentException("curriculum / marketing / development 중의 하나의 부서만 선택 가능합니다.");
        }

        // 이메일 중복 확인
        Optional<Admin> checkEmail = adminRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email이 존재합니다.");
        }

        // 사용자 ROLE 확인
        AdminRoleEnum role = AdminRoleEnum.STAFF;
        if (requestDto.isManager()) {
            if (!(department.equals("curriculum") || department.equals("development"))) {
                throw new IllegalArgumentException("커리큘럼 또는 개발 부서에서만 관리자 등록이 가능합니다.");
            }
            role = AdminRoleEnum.MANAGER;
        }

        // 사용자 등록
        Admin admin = new Admin(email, password, department, role);
        Admin saveAdmin = adminRepository.save(admin);
        return new SignupResponseDto(saveAdmin);
    }
}
