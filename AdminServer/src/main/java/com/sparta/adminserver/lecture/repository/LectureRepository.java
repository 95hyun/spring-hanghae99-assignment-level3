package com.sparta.adminserver.lecture.repository;

import com.sparta.adminserver.instructor.entity.Instructor;
import com.sparta.adminserver.lecture.entity.Lecture;
import com.sparta.adminserver.lecture.entity.LectureCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllByCategoryOrderByRegistrationDateDesc(LectureCategoryEnum category);

    List<Lecture> findAllByInstructorOrderByRegistrationDateDesc(Instructor instructor);
}
