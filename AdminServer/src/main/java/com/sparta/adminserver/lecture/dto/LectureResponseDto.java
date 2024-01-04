package com.sparta.adminserver.lecture.dto;

import com.sparta.adminserver.lecture.entity.Lecture;
import com.sparta.adminserver.lecture.entity.LectureCategoryEnum;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LectureResponseDto {
    private Long lectureId;
    private String lectureName;
    private long price;
    private String description;
    private LectureCategoryEnum category;
    private LocalDate registrationDate;
    private String name;

    public LectureResponseDto(Lecture saveLecture) {
        this.lectureId = saveLecture.getLectureId();
        this.lectureName = saveLecture.getLectureName();
        this.price = saveLecture.getPrice();
        this.description = saveLecture.getDescription();
        this.category = saveLecture.getCategory();
        this.registrationDate = saveLecture.getRegistrationDate();
        this.name = saveLecture.getInstructor().getName();
    }
}
