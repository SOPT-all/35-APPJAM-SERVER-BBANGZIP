package com.sopt.bbangzip.domain.piece.repository;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
     * 미완료 : is_finished 가 false 면서 오늘 날짜가 piece 의 deadline 과 같거나 이전인 piece 들
     */
    @Query("""
         SELECT COUNT(p)
         FROM Piece p
         WHERE p.isVisible = true
            AND p.isFinished = false
            AND p.deadline >= CURRENT DATE
            AND p.study.exam.subject.userSubject.user.id = :userId
     """)
    int countUnfinishedTodayPieces(Long userId);

    /**
     * 오늘 할 일 중, 완료한 공부 조각 들의 갯수 가져오기
     * 필요한 이유 : 몇 개나 완료했는지 뷰에서 보여줘야 함
     * 오늘 할 일 : is_visible 이 true 여야 됨
     * 미완료 : is_finished 가 false 면서 오늘 날짜가 piece 의 deadline 과 같거나 이전인 piece 들
     */
    @Query("""
            SELECT COUNT(p)
            FROM Piece p
            WHERE p.isVisible = true
                AND p.isFinished = true 
                AND p.deadline >= CURRENT DATE 
                AND p.study.exam.subject.userSubject.user.id = :userId
            """)
    int countFinishedTodayPieces(Long userId);
}
