package com.sparta.adminserver.lecture.service;

import com.sparta.adminserver.instructor.entity.Instructor;
import com.sparta.adminserver.instructor.repository.InstructorRepository;
import com.sparta.adminserver.lecture.dto.LectureRequestDto;
import com.sparta.adminserver.lecture.dto.LectureResponseDto;
import com.sparta.adminserver.lecture.entity.Lecture;
import com.sparta.adminserver.lecture.entity.LectureCategoryEnum;
import com.sparta.adminserver.lecture.repository.LectureRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final InstructorRepository instructorRepository;

    // 강의 등록
    @Transactional
    public LectureResponseDto createLecture(LectureRequestDto requestDto) {
        Instructor instructor = instructorRepository.findByName(requestDto.getName()).orElseThrow(()
                -> new EntityNotFoundException("입력된 강사는 등록된 강사가 아닙니다."));
        Lecture lecture = new Lecture(requestDto, instructor);
        Lecture saveLecture = lectureRepository.save(lecture);
        return new LectureResponseDto(saveLecture);
    }

    // 선택한 강의 정보 수정
    @Transactional
    public LectureResponseDto updatelecture(Long lectureId, LectureRequestDto requestDto) {
        // 해당 강의가 DB에 존재하는지?
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalArgumentException("선택한 강의는 존재하지 않습니다."));

        // 강의 정보 수정
        lecture.update(requestDto);
        Lecture saveLecture = lectureRepository.save(lecture);

        return new LectureResponseDto(saveLecture);
    }

    // 선택한 강의 조회
    public LectureResponseDto findLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(()
                -> new EntityNotFoundException("강의를 찾을 수 없습니다."));
        return new LectureResponseDto(lecture);
    }

    // 카테고리별 강의 목록 조회 - 강의 등록일 기준 내림차순 정렬
    public List<LectureResponseDto> getLecturesByCategory(LectureCategoryEnum category) {
        return lectureRepository.findAllByCategoryOrderByRegistrationDateDesc(category).stream().map(LectureResponseDto::new).toList();
    }


    // 해당 강사의 강의 목록 조회 - 강의 등록일 기준 내림차순 정렬
    public List<LectureResponseDto> findLectures(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow(()
                -> new EntityNotFoundException("강사를 찾을 수 없습니다."));

        return lectureRepository.findAllByInstructorOrderByRegistrationDateDesc(instructor).stream().map(LectureResponseDto::new).toList();
    }
}
