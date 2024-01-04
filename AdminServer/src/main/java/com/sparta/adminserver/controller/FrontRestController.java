package com.sparta.adminserver.controller;

import com.sparta.adminserver.admin.dto.SignupRequestDto;
import com.sparta.adminserver.admin.dto.SignupResponseDto;
import com.sparta.adminserver.admin.entity.AdminRoleEnum;
import com.sparta.adminserver.admin.service.AdminService;
import com.sparta.adminserver.instructor.dto.InstructorRequestDto;
import com.sparta.adminserver.instructor.dto.InstructorResponseDto;
import com.sparta.adminserver.instructor.service.InstructorService;
import com.sparta.adminserver.lecture.dto.LectureRequestDto;
import com.sparta.adminserver.lecture.dto.LectureResponseDto;
import com.sparta.adminserver.lecture.entity.LectureCategoryEnum;
import com.sparta.adminserver.lecture.service.LectureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class FrontRestController {

    private final AdminService adminService;
    private final InstructorService instructorService;
    private final LectureService lectureService;

    /**
     * 회원가입
     * adminService.signup을 통하여 여러가지 유효성 검사 후 DB에 admin 객체 저장 -> ResponseDto 반환
     * @param requestDto Validation 을 거친 회원가입 양식 (SignupRequestDto)
     * @param bindingResult Validation 양식에 어긋난 결과들에 대한 객체
     * @return 회원가입 로직 수행 (확인을 위한 SignupResponseDto 객체 반환)
     */
    @PostMapping("/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new IllegalArgumentException("유효한 형식이 아니어서 회원가입에 실패하였습니다.");
            // 유효성 검사 실패 시 클라이언트에게 에러 응답
        }
        return adminService.signup(requestDto);
    }

    /**
     * 강사 등록 기능
     * requestDto의 값들로 강사 객체 생성 -> DB 저장 -> ResponseDto 반환
     * @param requestDto 강사 정보 양식 (InstructorRequestDto)
     * @return 강사 등록 로직 수행 (InstructorResponseDto 반환)
     */
    @PostMapping("/instructor")
    public InstructorResponseDto createInstructor(@RequestBody InstructorRequestDto requestDto) {
        return instructorService.createInstructor(requestDto);
    }

    /**
     * 선택한 강사 정보 수정 기능
     * 강사 PK로 해당 강사 객체 호출 -> update 메서드로 entity 수정 -> DB 저장 -> ResponseDto 반환
     * @Secured MANAGER 권한을 받은 사용자(쿠키 -> JWT)만 메서드 접근 가능
     * @param instructorId 강사 Primary Key
     * @param requestDto 강사 정보 양식 (InstructorRequestDto)
     * @return 강사 등록 후 ResponseDto 반환
     */
    @Secured(AdminRoleEnum.Authority.MANAGER) // 매니저만 등록 가능
    @PutMapping("/instructor/{instructorId}")
    public InstructorResponseDto updateInstructor(@PathVariable Long instructorId, @RequestBody InstructorRequestDto requestDto) {
        return instructorService.updateInstructor(instructorId, requestDto);
    }

    /**
     * 선택한 강사 조회 기능
     * 강사 PK로 해당 강사 객체 호출
     * @param instructorId 강사 Primary Key
     * @return 조회 로직 수행 후 ResponseDto 반환
     */
    @GetMapping("/instructor/{instructorId}")
    public InstructorResponseDto findInstructor(@PathVariable Long instructorId) {
        return instructorService.findInstructor(instructorId);
    }

    /**
     * 선택한 강사가 촬영한 강의 목록 조회 기능
     * 강사 PK로 강사 repository 에서 해당 강사 객체 조회
     * -> 강의 repository에서 해당 강사 객체를 필드로 갖고 있는 강의 객체 리스트 호출
     * -> ResponseDto List로 변환하여 반환
     * @param instructorId 강사 Primary Key
     * @return 목록 조회 로직 후 ResponseDto 반환
     */
    @GetMapping("/instructor/{instructorId}/lecture")
    public List<LectureResponseDto> getLectureList (@PathVariable Long instructorId) {
        return lectureService.findLectures(instructorId);
    }

    /**
     * 강의 등록 기능
     * requestDto의 값들로 강의 객체 생성 -> DB 저장 -> ResponseDto 반환
     * (참고) LectureRequestDto 에는 강사명(name) 을 필드로 갖고 있음.
     * 이 강사명을 통하여 강사 repository에 해당 강사가 있으면 강사 객체를 호출하여 lecture entity에 주입함
     * @param requestDto 강의 정보 양식 (LectureRequestDto)
     * @return 강의 등록 후 ResponseDto 반환
     */
    @PostMapping("/lecture")
    public LectureResponseDto createLecture(@RequestBody LectureRequestDto requestDto) {
        return lectureService.createLecture(requestDto);
    }

    /**
     * 선택한 강의 정보 수정 기능
     * @Secured MANAGER 권한을 받은 사용자(쿠키 -> JWT)만 메서드 접근 가능
     * @param lectureId 강의 PrimaryKey
     * @param requestDto 강의 정보 (LectureRequestDto)
     * @return 강의 정보 수정 후 ResponseDto 반환
     */
    @Secured(AdminRoleEnum.Authority.MANAGER)
    @PutMapping("/lecture/{lectureId}")
    public LectureResponseDto updateLecture(@PathVariable Long lectureId, @RequestBody LectureRequestDto requestDto) {
        return lectureService.updatelecture(lectureId, requestDto);
    }

    /**
     * 선택한 강의 조회 기능 (get)
     * 해당 강의 PK로 repository 에서 해당 강의 객체 조회
     * @param lectureId 강의 PrimaryKey
     * @return 강의 조회후 호출된 객체 ResponseDto 반환
     */
    @GetMapping("/lecture/{lectureId}")
    public LectureResponseDto findLecture (@PathVariable Long lectureId) {
        return lectureService.findLecture(lectureId);
    }

    /**
     * 카테고리별 강의 목록 조회 기능 (get)
     * 해당 카테고리로 repository 에서 해당 강의 List 조회
     * @param category (SPRING || REACT || NODE) '대문자'로 입력해야 Postman 테스트 가능 (Enum Type)
     * @return 강의 List 조회 후 ResponseDto List 반환
     */
    @GetMapping("/lecture/category/{category}")
    public List<LectureResponseDto> getLecturesByCategory(@PathVariable LectureCategoryEnum category) {
        return lectureService.getLecturesByCategory(category);
    }
}

