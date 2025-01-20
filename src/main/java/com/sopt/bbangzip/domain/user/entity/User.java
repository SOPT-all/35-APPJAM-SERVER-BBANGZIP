package com.sopt.bbangzip.domain.user.entity;

import com.sopt.bbangzip.common.constants.entity.UserTableConstants;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = UserTableConstants.TABLE_USER)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = UserTableConstants.COLUMN_ID)
    private Long id;

    @Column(name = UserTableConstants.COLUMN_PLATFORM_USER_ID, nullable = false, unique = true)
    private Long platformUserId;

    @Column(name = UserTableConstants.COLUMN_PLATFORM, nullable = false)
    private String platform;

    @Column(name = UserTableConstants.COLUMN_NICKNAME)
    private String nickname;

    @Column(name = UserTableConstants.COLUMN_IS_ONBOARDING_COMPLETE, nullable = false)
    private Boolean isOnboardingComplete;

    @Column(name = UserTableConstants.COLUMN_USER_POINT, nullable = false)
    private int point = 0;

    @Column(name = UserTableConstants.COLUMN_USER_LEVEL, nullable = false)
    private int userLevel;

    @Column(name = UserTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = UserTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSubject> userSubjects;

    @Builder
    public User(Long platformUserId, String platform, String nickname, Boolean isOnboardingComplete, int userLevel) {
        this.platformUserId = platformUserId;
        this.platform = platform;
        this.nickname = nickname;
        this.isOnboardingComplete = isOnboardingComplete;
        this.userLevel = userLevel;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateUser(String nickname, Boolean isOnboardingComplete) {
        if (nickname != null && !nickname.isEmpty()) {
            this.nickname = nickname;
        }
        if(isOnboardingComplete != null) {
            this.isOnboardingComplete = isOnboardingComplete;
        }
    }

    /**
     * 뱃지 관련 필드들
     */
    @Column(name = UserTableConstants.COLUMN_FIRST_STUDY_COMPLETED_AT)
    private LocalDateTime firstStudyCompletedAt = null; // 빵 굽기 시작 획득 여부 (맨 처음으로 '학습 완료')

    @Column(name = UserTableConstants.COLUMN_FIRST_TODAY_TASKS_COMPLETED_AT)
    private LocalDateTime allTasksCompletedAt = null; // 오늘의 빵 완판 획득 여부 (맨 처음으로 '오늘 할 일'을 모두 완료)

    @Column(name = UserTableConstants.COLUMN_HAS_MASS_BAKING_BREAD_BADGE)
    private LocalDateTime hasMassBakingBreadBadge = null; // 빵 대량 생산 뱃지 획득 여부

    @Column(name = UserTableConstants.COLUMN_HAS_PREPARING_OPENING_BAKERY)
    private LocalDateTime hasPreparingOpeningBakery = null; // 빵집 오픈 준비중 뱃지 획득 여부


    @SuppressWarnings("FieldMayBeFinal")
    @Column(name = UserTableConstants.COLUMN_TODAY_STUDY_COMPLETE_COUNT, nullable = false)
    private int todayStudyCompleteCount = 0; // 하루 기준 "학습 완료" 횟수 (빵 대량 생산, 하루기준 '학습 완료' 3회 완료한 경우)

    @Column(name = UserTableConstants.COLUMN_LAST_STUDY_COMPLETED_DATE)
    private LocalDateTime lastStudyCompletedDate; // 마지막 "학습 완료" 날짜 : 날짜가 변경되면 todayStudyCompleteCount를 초기화하기 위해 사용

    /**
     * 뱃지 관련 로직들
     */
    public void markFirstStudyComplete() { // 맨 처음으로 '학습 완료' (빵 굽기 시작)
        if (this.firstStudyCompletedAt == null) {
            this.firstStudyCompletedAt = LocalDateTime.now();
            this.point += 100;
        }
    }

    public void markFirstTodayTasksCompletedAt() { // 맨 처름으로 '오늘 할 일'을 모두 완료 (오늘의 빵 완판)
        if (this.allTasksCompletedAt == null) {
            this.allTasksCompletedAt = LocalDateTime.now();
            this.point += 200;
        }
    }

    public void markHasMassBakingBreadBadge() { // '빵 대량 생산' 뱃지 획득하면 업데이트
        if (this.hasMassBakingBreadBadge == null) {
            this.hasMassBakingBreadBadge = LocalDateTime.now();
            this.point += 50;
        }
    }

    public void markHasPreparingOpeningBakery(){
        if (this.hasPreparingOpeningBakery == null) {
            this.hasPreparingOpeningBakery = LocalDateTime.now();
            this.point +=50;
        }
    }

    public void incrementTodayStudyCompleteCount() {
        LocalDateTime now = LocalDateTime.now();
        if (lastStudyCompletedDate == null || !lastStudyCompletedDate.toLocalDate().isEqual(LocalDateTime.now().toLocalDate())) {
            this.todayStudyCompleteCount = 0; // 날짜가 바뀌면 초기화
        }
        this.todayStudyCompleteCount++;
        this.lastStudyCompletedDate = now;
    }

    /**
     * 뱃지 개수를 반환
     */
    public int getBadgeCount() {
        int badgeCount = 0;
        if (this.firstStudyCompletedAt != null) badgeCount++;
        if (this.allTasksCompletedAt != null) badgeCount++;
        if (this.hasMassBakingBreadBadge != null) badgeCount++;
        return badgeCount;
    }

    // level 갱신
    public void updateUserLevel(int newLevel) {
        if (this.userLevel != newLevel) {
            this.userLevel = newLevel;
            this.updatedAt = LocalDateTime.now(); // 레벨 변경 시 updatedAt 갱신
        }
    }
}