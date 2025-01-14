package com.sopt.bbangzip.domain.userSubject.entity;

import com.sopt.bbangzip.common.constants.entity.UserSubjectTableConstants;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = UserSubjectTableConstants.TABLE_USER_SUBJECT)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UserSubjectTableConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = UserSubjectTableConstants.COLUMN_YEAR, nullable = false)
    private int year;

    @Column(name = UserSubjectTableConstants.COLUMN_SEMESTER, nullable = false)
    private String semester;

    @OneToMany(mappedBy = "userSubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

    @Builder
    public UserSubject(User user, int year, String semester) {
        this.user = user;
        this.year = year;
        this.semester = semester;
    }
}
