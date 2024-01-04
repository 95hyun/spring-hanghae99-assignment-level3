package com.sparta.adminserver.lecture.dto;

import com.sparta.adminserver.lecture.entity.LectureCategoryEnum;
import lombok.Getter;

@Getter
public class LectureRequestDto {
    // 강의명, 가격, 소개, 카테고리, 강사(명), 등록일

    private String lectureName;
    private long price;
    private String description;
    private LectureCategoryEnum category; // SPRING, REACT, NODE
    private String name;

}