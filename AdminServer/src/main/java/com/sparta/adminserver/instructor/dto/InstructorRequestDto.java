package com.sparta.adminserver.instructor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InstructorRequestDto {
    private String name;
    private int experienceYears;
    private String company;
    private String phoneNumber;
    private String introduction;
}
