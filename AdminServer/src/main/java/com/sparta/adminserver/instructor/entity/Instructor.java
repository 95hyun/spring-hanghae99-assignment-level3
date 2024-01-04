package com.sparta.adminserver.instructor.entity;

import com.sparta.adminserver.instructor.dto.InstructorRequestDto;
import com.sparta.adminserver.lecture.entity.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    private String name;
    private int experienceYears;
    private String company;
    private String phoneNumber;
    private String introduction;

    public Instructor(InstructorRequestDto requestDto) {
        this.name = requestDto.getName();
        this.experienceYears = requestDto.getExperienceYears();
        this.company = requestDto.getCompany();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.introduction = requestDto.getIntroduction();
    }

    public void update(InstructorRequestDto requestDto) { // 이름은 수정 불가
        this.experienceYears = requestDto.getExperienceYears();
        this.company = requestDto.getCompany();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.introduction = requestDto.getIntroduction();
    }

//    @ManyToMany(mappedBy = "instructors")
//    private Set<Lecture> lectures = new HashSet<>();

    // Constructor, Getters, Setters, etc.
}
