package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.DuplicateSubjectException;
import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import com.sopt.bbangzip.domain.user.repository.UserRepository;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.repository.UserSubectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final UserRepository userRepository;
    private final UserSubectRepository userSubectRepository;
    private final SubjectRepository subjectRepository;

    public void createSubject(Long userId, SubjectCreateDto subjectCreateDto) {
        // 1. 유저 확인
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.UNAUTHORIZED);
        }

        // 2. 년도/학기 확인 및 생성
        UserSubject userSubject = userSubectRepository.findByUser_IdAndYearAndSemester(
                        userId, subjectCreateDto.year(), subjectCreateDto.semester())
                .orElseGet(() -> userSubectRepository.save(
                        new UserSubject(
                                userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.UNAUTHORIZED)),
                                subjectCreateDto.year(),
                                subjectCreateDto.semester()
                        )
                ));

        // 3. 동일 학기에 중복된 과목명 확인
        if (subjectRepository.existsByUserSubjectAndSubjectName(userSubject, subjectCreateDto.subjectName())) {
            throw new DuplicateSubjectException(ErrorCode.NOT_FOUND_SUBJECT);
        }

        // 4. 과목 추가
        Subject subject = new Subject(
                subjectCreateDto.subjectName(),
                userSubject
        );
        subjectRepository.save(subject);
    }
}
