package com.sopt.bbangzip.domain.exam.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.domain.exam.api.dto.response.ExamResponseDto;
import com.sopt.bbangzip.domain.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    /**
     * 특정 시험 정보 조회 (중간고사/기말고사 구분)
     *
     * @param subjectId 과목 ID
     * @param examName  시험 이름 ("mid" 또는 "fin")
     */
    @GetMapping("/exams/{subjectId}/{examName}")
    public ResponseEntity<ExamResponseDto> getExamInfo(
            @UserId final long userId,
            @PathVariable final String examName,
            @PathVariable final long subjectId
    ) {
        return ResponseEntity.ok(examService.getExamInfoWithConversion(userId, examName, subjectId));
    }
}


