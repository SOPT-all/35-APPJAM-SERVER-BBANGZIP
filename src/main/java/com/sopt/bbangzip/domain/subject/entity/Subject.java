package com.sopt.bbangzip.domain.subject.entity;

import com.sopt.bbangzip.common.constants.entity.SubjectTableConstants;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = SubjectTableConstants.TABLE_SUBJECT)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SubjectTableConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SubjectTableConstants.COLUMN_USER_SUBJECT_ID, nullable = false)
    private UserSubject userSubject;

    @Column(name = SubjectTableConstants.COLUMN_SUBJECT_NAME, nullable = false)
    private String subjectName;

    @Column(name = SubjectTableConstants.COLUMN_MOTIVATION_MESSAGE)
    private String motivationMessage;

    @Column(name = SubjectTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = SubjectTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Exam> exams = new ArrayList<>();

    @Builder
    public Subject(UserSubject userSubject, String subjectName, String motivationMessage) {
        this.userSubject = userSubject;
        this.subjectName = subjectName;
        this.motivationMessage = motivationMessage;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateSubjectName(String subjectName) {
        this.subjectName = subjectName;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateMotivationMessage(String motivationMessage) {
        this.motivationMessage = motivationMessage;
        this.updatedAt = LocalDateTime.now();
    }

}
