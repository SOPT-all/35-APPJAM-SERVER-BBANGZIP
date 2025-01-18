package com.sopt.bbangzip.domain.piece.repository;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {

    // 유저가 선택한 공부 조각 가져오는
    @Query("""
        SELECT p
        FROM Piece p
        JOIN p.study s
        JOIN s.exam e
        JOIN e.subject sub
        JOIN sub.userSubject us
        JOIN us.user u
        WHERE p.id = :pieceId AND u.id = :userId
    """)
    Optional<Piece> findByPieceIdAndUserId(@Param("pieceId") Long pieceId, @Param("userId") Long userId);


    /**
     * 오늘 할 일 중, 미완료한 공부 조각 들의 갯수 가져오기
     * 필요한 이유 : '오늘 할 일'을 최초로 다 완료 했을 경우 <오늘의 빵 완판> 을 부여해야 하는데, 이 개수가 0 이면 '오늘 할 일'을 최초로 다 완료한 것이 됨
     * 또한, 몇 개나 남았는지 뷰에서 보여줘야 함
     * <p>
     * 오늘 할 일 : is_visible 이 true 여야 됨
     * 미완료 : is_finished 가 false
     */
    @Query("""
         SELECT COUNT(p)
         FROM Piece p
         WHERE p.isVisible = true
            AND p.isFinished = false
            AND p.study.exam.subject.userSubject.user.id = :userId
     """)
    int countUnfinishedTodayPieces(Long userId);

    /**
     * 오늘 할 일 중, 완료한 공부 조각 들의 갯수 가져오기
     * 필요한 이유 : 몇 개나 완료했는지 뷰에서 보여줘야 함
     * 오늘 할 일 : is_visible 이 true 여야 됨
     * 완료 : is_finished 가 true
     */
    @Query("""
            SELECT COUNT(p)
            FROM Piece p
            WHERE p.isVisible = true
                AND p.isFinished = true
                AND p.study.exam.subject.userSubject.user.id = :userId
            """)
    int countFinishedTodayPieces(Long userId);

    /**
     * 밀린 공부 조각들의 갯수 가져오기
     * 밀림 : is_visible 이 false 면서, is_finished 가 false 면서, 오늘 날짜가 piece 의 deadline 보다 이후인 piece 들
     */
    @Query("""
             SELECT COUNT(p)
             FROM Piece p
             WHERE p.isVisible = false
               AND p.isFinished=false
               AND p.deadline < CURRENT DATE
               AND p.study.exam.subject.userSubject.user.id = :userId
            """)
    int countPendingTodayPieces(Long userId);

    /**
     * [todo - 최근 등록 순]
     * 1순위 - 최신 등록순
     * 2순위 - 과목 내 파트 순서
     * 오늘 할 일 : is_visible 이 true 여야 됨
     */
    @Query("""
    SELECT p
    FROM Piece p
    WHERE p.study.exam.subject.userSubject.user.id = :userId
        AND p.study.exam.subject.userSubject.year = :year
        AND p.study.exam.subject.userSubject.semester = :semester
        AND p.isVisible = true
    ORDER BY p.createdAt DESC, p.pieceNumber ASC
    """)
    List<Piece> findTodoPiecesByRecentOrder(Long userId, int year, String semester);

    /**
     * [todo - 분량 적은 순]
     * 1순위 - 분량 적은 양부터 (학습 범위 오름차순)
     * 2순위 - 마감 기한 빠른 순(오름차 순=오늘 날짜로부터 가까운 순)
     * 3순위 - 과목 명 가나다 순
     * 4순위 - 과목 내 파트 순서
     * 오늘 할 일 : is_visible 이 true 여야 됨
     */
    @Query("""
    SELECT p
    FROM Piece p
    WHERE p.study.exam.subject.userSubject.user.id = :userId
        AND p.isVisible = true
        AND p.study.exam.subject.userSubject.year = :year
        AND p.study.exam.subject.userSubject.semester = :semester
    ORDER BY p.pageAmount ASC, p.deadline ASC,
             p.study.exam.subject.subjectName ASC,
             p.pieceNumber ASC
    """)
    List<Piece> findTodoPiecesByLeastVolumeOrder(Long userId, int year, String semester);

    /**
     * [todo - 마감 기한 빠른 순]
     * 1순위 - 마감 기한 빠른 순(오름차 순=오늘 날짜로부터 가까운 순)
     * 2순위 - 적은 양부터 (학습 범위 오름차순)
     * 3순위 - 과목 명 가나다 순
     * 4순위 - 과목 내 파트 순서
     * 오늘 할 일 : is_visible 이 true 여야 됨
     */
    @Query("""
    SELECT p
    FROM Piece p
    WHERE p.study.exam.subject.userSubject.user.id = :userId
        AND p.study.exam.subject.userSubject.year = :year
        AND p.study.exam.subject.userSubject.semester = :semester
        AND p.isVisible = true
    ORDER BY p.deadline ASC,
             p.pageAmount ASC,
             p.study.exam.subject.subjectName ASC,
             p.pieceNumber ASC
    """)
    List<Piece> findTodoPiecesByNearestDeadlineOrder(Long userId, int year, String semester);

    /**
     * [pending - 최근 등록 순]
     * 1순위 - 최신 등록순
     * 2순위 - 과목 내 파트 순
     * 밀린 일 : is_visible 이 false 면서, is_finished 가 false 면서, 오늘 날짜가 piece 의 deadline 보다 이후인 piece 들
     */
    @Query("""
    SELECT p
    FROM Piece p
    WHERE p.study.exam.subject.userSubject.user.id = :userId
        AND p.isVisible = false
        AND p.isFinished = false
        AND p.deadline < CURRENT_DATE
        AND p.study.exam.subject.userSubject.year = :year
        AND p.study.exam.subject.userSubject.semester = :semester
    ORDER BY p.createdAt DESC, p.pieceNumber ASC
    """)
    List<Piece> findPendingPiecesByRecentOrder(Long userId, int year, String semester);

    /**
     * [pending - 분량 적은 순]
     * 1순위 - 분량 적은 양부터 (학습 범위 오름차순)
     * 2순위 - 마감 기한 빠른 순(오름차 순=오늘 날짜로부터 가까운 순)
     * 3순위 - 과목 명 가나다 순
     * 4순위 - 과목 내 파트 순서
     * 밀린 일 : is_visible 이 false 면서, is_finished 가 false 면서, 오늘 날짜가 piece 의 deadline 보다 이후인 piece 들
     */
    @Query("""
    SELECT p
    FROM Piece p
    WHERE p.study.exam.subject.userSubject.user.id = :userId
        AND p.isVisible = false
        AND p.isFinished = false
        AND p.deadline < CURRENT_DATE
        AND p.study.exam.subject.userSubject.year = :year
        AND p.study.exam.subject.userSubject.semester = :semester
    ORDER BY p.pageAmount ASC, p.deadline ASC, 
             p.study.exam.subject.subjectName ASC, p.pieceNumber ASC
    """)
    List<Piece> findPendingPiecesByLeastVolumeOrder(Long userId, int year, String semester);

    /**
     * [pending - 마감 기한 빠른 순]
     * 1순위 - 마감 기한 빠른 순(오름차 순=오늘 날짜로부터 가까운 순)
     * 2순위 - 적은 양부터 (학습 범위 오름차순)
     * 3순위 - 과목 명 가나다 순
     * 4순위 - 과목 내 파트 순서
     * 밀린 일 : is_visible 이 false 면서, is_finished 가 false 면서, 오늘 날짜가 piece 의 deadline 보다 이후인 piece 들
     */
    @Query("""
    SELECT p
    FROM Piece p
    WHERE p.study.exam.subject.userSubject.user.id = :userId
        AND p.isVisible = false
        AND p.isFinished = false
        AND p.deadline < CURRENT_DATE
        AND p.study.exam.subject.userSubject.year = :year
        AND p.study.exam.subject.userSubject.semester = :semester
    ORDER BY p.deadline ASC, p.pageAmount ASC, 
             p.study.exam.subject.subjectName ASC, p.pieceNumber ASC
    """)
    List<Piece> findPendingPiecesByNearestDeadlineOrder(Long userId, int year,  String semester);
}