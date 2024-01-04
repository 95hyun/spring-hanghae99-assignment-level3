package com.sparta.adminserver.lecture.entity;

import com.sparta.adminserver.instructor.entity.Instructor;
import com.sparta.adminserver.lecture.dto.LectureRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Lecture extends LectureDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;

    private String lectureName;
    private long price;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private LectureCategoryEnum category; // SPRING, REACT, NODE

    @ManyToOne
    private Instructor instructor;

    public Lecture(LectureRequestDto requestDto, Instructor instructor) {
        this.lectureName = requestDto.getLectureName();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
        this.category = requestDto.getCategory();
        this.instructor = instructor;
    }

    // 강의명, 가격, 소개, 카테고리를 수정할 수 있습니다.
    public void update(LectureRequestDto requestDto) {
        this.lectureName = requestDto.getLectureName();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
        this.category = requestDto.getCategory();
    }


//    @ManyToMany
//    @JoinTable(
//            name = "lecture_instructor",
//            joinColumns = @JoinColumn(name = "lecture_id"),
//            inverseJoinColumns = @JoinColumn(name = "instructor_id"))
//    private Set<Instructor> instructors = new HashSet<>();



    // Constructor, Getters, Setters, etc.
}
