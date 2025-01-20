package com.sopt.bbangzip.domain.exam.repository;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    /**
     * 과목 ID와 시험 이름으로 Exam 및 관련 User 조회
     *
     * @param subjectId 과목 ID
     * @param examName  시험 이름 ("중간고사" 또는 "기말고사")
     * @return Optional<Exam>
     */
    @Query("""
            SELECT e
            FROM Exam e
            JOIN e.subject s
            JOIN s.userSubject us
            JOIN us.user u
            WHERE s.id = :subjectId
              AND e.examName = :examName
           """)
    Optional<Exam> findBySubjectIdAndExamNameWithUser(@Param("subjectId") Long subjectId,
                                                      @Param("examName") String examName);

}

