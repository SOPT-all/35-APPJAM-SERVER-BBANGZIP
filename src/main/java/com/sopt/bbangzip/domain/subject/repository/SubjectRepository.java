package com.sopt.bbangzip.domain.subject.repository;

import com.sopt.bbangzip.domain.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
