package com.sparta.adminserver.instructor.service;

import com.sparta.adminserver.instructor.dto.InstructorRequestDto;
import com.sparta.adminserver.instructor.dto.InstructorResponseDto;
import com.sparta.adminserver.instructor.entity.Instructor;
import com.sparta.adminserver.instructor.repository.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;

    @Transactional
    // 강사 등록
    public InstructorResponseDto createInstructor(InstructorRequestDto requestDto) {
        Instructor instructor = new Instructor(requestDto);
        Instructor saveInstructor = instructorRepository.save(instructor);
        return new InstructorResponseDto(saveInstructor);
    }

    // 선택한 강사정보 수정
    @Transactional
    public InstructorResponseDto updateInstructor(Long instructorId, InstructorRequestDto requestDto) {
        // 해당 강사가 DB에 존재하는지?
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(() ->
                new IllegalArgumentException("선택한 강사는 존재하지 않습니다."));

        // 강사 정보 수정
        instructor.update(requestDto);
        Instructor saveInstructor = instructorRepository.save(instructor);
        return new InstructorResponseDto(saveInstructor);
    }

    // 선택한 강사 정보 조회(호출)
    public InstructorResponseDto findInstructor(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(()
                -> new EntityNotFoundException("강사를 찾을 수 없습니다."));

        return new InstructorResponseDto(instructor);
    }
}
