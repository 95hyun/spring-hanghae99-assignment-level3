package com.sparta.adminserver.instructor.repository;

import com.sparta.adminserver.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    Optional<Instructor> findByName(String name);
}
