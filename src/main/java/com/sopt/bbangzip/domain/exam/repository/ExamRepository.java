package com.sopt.bbangzip.domain.exam.repository;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
}
