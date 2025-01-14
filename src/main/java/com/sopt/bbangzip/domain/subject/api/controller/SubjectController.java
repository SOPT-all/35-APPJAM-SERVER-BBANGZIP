package com.sopt.bbangzip.domain.subject.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.common.exception.base.DuplicateSubjectException;
import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.subject.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/subjects")
    public ResponseEntity<ResponseDto<Void>> createSubject(
            @UserId final Long userId,
            @RequestBody @Valid final SubjectCreateDto subjectCreateDto
    ) {

        try {
            subjectService.createSubject(userId, subjectCreateDto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseDto.success(null)); // 성공 시 204
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ResponseDto.fail(e.getErrorCode())); // 404 에러
        } catch (DuplicateSubjectException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDto.fail(e.getErrorCode())); // 400 에러
        }
    }

}
