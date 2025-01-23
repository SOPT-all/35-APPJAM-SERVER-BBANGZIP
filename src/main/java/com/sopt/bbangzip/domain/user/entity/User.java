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
    private int userLevel = 1;


    @Column(name = UserTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = UserTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSubject> userSubjects;

    @Builder
    public User(Long platformUserId, String platform, String nickname, Boolean isOnboardingComplete) {
        this.platformUserId = platformUserId;
        this.platform = platform;
        this.nickname = nickname;
        this.isOnboardingComplete = isOnboardingComplete;
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
     * 뱃지 관련 획득 여부 필드들
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
    @Column(name = UserTableConstants.COLUMN_FIRST_CREATE_STUDY_COUNT, nullable = false)
    private int firstCreateStudyCount = 0;

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

    public void markFirstTodayTasksCompletedAt() { // 맨 처음으로 '오늘 할 일'을 모두 완료 (오늘의 빵 완판)
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

    public void markHasPreparingOpeningBakery(){ // 맨 처음으로 '공부할 내용'을 추가 (빵집 오픈 준비 중)
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

    public void  incrementFirstCreateStudyCount(){
        this.firstCreateStudyCount++;
    }

    /**
     * 뱃지 개수를 반환
     */
    public int getBadgeCount() {
        int badgeCount = 0;
        if (this.firstStudyCompletedAt != null) badgeCount++;
        if (this.allTasksCompletedAt != null) badgeCount++;
        if (this.hasMassBakingBreadBadge != null) badgeCount++;
        if (this.hasPreparingOpeningBakery != null) badgeCount++;
        return badgeCount;
    }

    // level 갱신
    public void updateUserLevel(int newLevel) {
        if (this.userLevel != newLevel) {
            this.userLevel = newLevel;
            this.updatedAt = LocalDateTime.now(); // 레벨 변경 시 updatedAt 갱신
        }
    }

    /**
     * 앱잼을 위한 Mock Badge 를 위한 필드들.
     * 정말 ONLY APPZAM 만을 위한.
     */
    @Column(name = UserTableConstants.COLUMN_ESCAPE_BADGE_1)
    private LocalDateTime escapeBadge1 = null; // 미룬이 탈출 1

    @Column(name = UserTableConstants.COLUMN_ESCAPE_BADGE_AAPJAM)
    private LocalDateTime escapeBadgeAapjam = null; // 미룬이 탈출 앱잼

    @Column(name = UserTableConstants.COLUMN_ESCAPE_BARELY_BADGE_1)
    private LocalDateTime escapeBarelyBadge1 = null; // 미룬이 겨우 탈출 1

    @Column(name = UserTableConstants.COLUMN_ESCAPE_BARELY_BADGE_2)
    private LocalDateTime escapeBarelyBadge2 = null; // 미룬이 겨우 탈출 2

    @Column(name = UserTableConstants.COLUMN_ESCAPE_BARELY_BADGE_3)
    private LocalDateTime escapeBarelyBadge3 = null; // 미룬이 겨우 탈출 3

    @Column(name = UserTableConstants.COLUMN_INSSA_BOSS_BADGE_1)
    private LocalDateTime inssaBossBadge1 = null; // 인싸 보스 1

    @Column(name = UserTableConstants.COLUMN_INSSA_BOSS_BADGE_2)
    private LocalDateTime inssaBossBadge2 = null; // 인싸 보스 2

    public void markEscapeBadge1() {
        if (this.escapeBadge1 == null) {
            this.escapeBadge1 = LocalDateTime.now();
            this.point += 30;
        }
    }

    public void markEscapeBadgeAapjam() {
        if (this.escapeBadgeAapjam == null) {
            this.escapeBadgeAapjam = LocalDateTime.now();
            this.point += 40;
        }
    }

    public void markEscapeBarelyBadge1() {
        if (this.escapeBarelyBadge1 == null) {
            this.escapeBarelyBadge1 = LocalDateTime.now();
            this.point += 20;
        }
    }

    public void markEscapeBarelyBadge2() {
        if (this.escapeBarelyBadge2 == null) {
            this.escapeBarelyBadge2 = LocalDateTime.now();
            this.point += 25;
        }
    }

    public void markEscapeBarelyBadge3() {
        if (this.escapeBarelyBadge3 == null) {
            this.escapeBarelyBadge3 = LocalDateTime.now();
            this.point += 30;
        }
    }

    public void markInssaBossBadge1() {
        if (this.inssaBossBadge1 == null) {
            this.inssaBossBadge1 = LocalDateTime.now();
            this.point += 50;
        }
    }

    public void markInssaBossBadge2() {
        if (this.inssaBossBadge2 == null) {
            this.inssaBossBadge2 = LocalDateTime.now();
            this.point += 60;
        }
    }
}