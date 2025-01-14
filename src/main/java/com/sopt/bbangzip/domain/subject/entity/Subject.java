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
@Getter @Setter
@Table(name = SubjectTableConstants.TABLE_SUBJECT)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = SubjectTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Exam> exams = new ArrayList<>();

    public Subject(String subjectName, UserSubject userSubject) {
        this.subjectName = subjectName;
        this.userSubject = userSubject;
    }

}
