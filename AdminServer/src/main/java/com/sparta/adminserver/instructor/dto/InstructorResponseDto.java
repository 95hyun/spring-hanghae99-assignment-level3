package com.sparta.adminserver.instructor.dto;

import com.sparta.adminserver.instructor.entity.Instructor;
import lombok.Getter;

@Getter
public class InstructorResponseDto {
    private Long instructorId;
    private String name;
    private int experienceYears;
    private String company;
    private String phoneNumber;
    private String introduction;


    public InstructorResponseDto(Instructor saveInstructor) {
        this.instructorId = saveInstructor.getInstructorId();
        this.name = saveInstructor.getName();
        this.experienceYears = saveInstructor.getExperienceYears();
        this.company = saveInstructor.getCompany();
        this.phoneNumber = saveInstructor.getPhoneNumber();
        this.introduction = saveInstructor.getIntroduction();
    }
}
