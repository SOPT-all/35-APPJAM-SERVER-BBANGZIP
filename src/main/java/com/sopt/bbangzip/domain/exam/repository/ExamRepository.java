package com.sopt.bbangzip.domain.exam.repository;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
