package com.sopt.bbangzip.domain.study.repository;

import com.sopt.bbangzip.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {
}
