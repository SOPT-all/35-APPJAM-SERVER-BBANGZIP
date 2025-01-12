package com.sopt.bbangzip.domain.userSubject.entity;

import com.sopt.bbangzip.common.constants.entity.UserSubjectTableConstants;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = UserSubjectTableConstants.TABLE_USER_SUBJECT)
public class UserSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UserSubjectTableConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserSubjectTableConstants.COLUMN_USER_ID, nullable = false)
    private User user;

    @Column(name = UserSubjectTableConstants.COLUMN_YEAR, nullable = false)
    private Integer year;

    @Column(name = UserSubjectTableConstants.COLUMN_SEMESTER, nullable = false)
    private String semester;

    @OneToMany(mappedBy = "userSubject", cascade = CascadeType.ALL)
    private List<Subject> subjects;

}
