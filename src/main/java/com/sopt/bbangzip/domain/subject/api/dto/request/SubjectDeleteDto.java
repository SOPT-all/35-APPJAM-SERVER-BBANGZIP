package com.sopt.bbangzip.domain.subject.api.dto.request;

import java.util.List;

public record SubjectDeleteDto(int year, String semester, List<Long> subjectIds) {}
