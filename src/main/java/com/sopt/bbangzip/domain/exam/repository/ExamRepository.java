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
    @Query("SELECT e FROM Exam e WHERE e.subject.id = :subjectId AND e.examName = :examName AND e.examDate = :examDate")
    Optional<Exam> findBySubjectIdAndExamNameAndExamDate(@Param("subjectId") Long subjectId,
                                                         @Param("examName") String examName,
                                                         @Param("examDate") LocalDate examDate);

}
