package com.sopt.bbangzip.domain.subject.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SubjectResponseDto(
        int year, // 학년
        String semester, // 학기
        List<SubjectDto> subjectList // 과목 리스트
) {
    @Builder
    public record SubjectDto(
            Long subjectId, // 과목 ID
            String subjectName, // 과목명
            List<StudyDto> studyList // 시험 정보 리스트
    ) {
        @Builder
        public record StudyDto(
                String examName, // 시험 이름
                int examDDay, // 시험까지 남은 일수
                int pendingCount, // 밀린 공부 개수
                int remainingCount // 남은 공부 개수
        ) {}
    }
}

