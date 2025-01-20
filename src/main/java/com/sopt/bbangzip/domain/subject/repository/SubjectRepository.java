package com.sopt.bbangzip.domain.subject.repository;

import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    //  UserSubject와 SubjectName으로 Subject 존재 여부 확인
    @Query("""
    SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
    FROM Subject s
    JOIN s.userSubject us
    JOIN us.user u
    WHERE us.id = :userSubjectId AND u.id = :userId AND s.subjectName = :subjectName
""")
    boolean existsByUserSubjectAndSubjectNameAndUserId( Long userSubjectId, Long userId, String subjectName);


    @Query("""
    SELECT s
    FROM Subject s
    JOIN s.userSubject us
    JOIN us.user u
    WHERE s.id IN :subjectIds AND us.id = :userSubjectId AND u.id = :userId
""")
    List<Subject> findByIdInAndUserSubjectIdAndUserId(List<Long> subjectIds, Long userSubjectId, Long userId);


    // 유저 ID와 과목 ID로 과목 조회
    @Query("""
    SELECT s 
    FROM Subject s
    JOIN s.userSubject us
    JOIN us.user u
    WHERE s.id = :subjectId AND u.id = :userId
""")
    Optional<Subject> findByIdAndUserId(Long subjectId, Long userId);

    // 학기별 과목 목록 조회
    @Query("""
            SELECT s
            FROM Subject s
            JOIN s.userSubject us
            WHERE us.user.id = :userId
              AND us.year = :year
              AND us.semester = :semester
           """)
    List<Subject> findSubjectsByUserAndSemester(Long userId, int year, String semester);
}
