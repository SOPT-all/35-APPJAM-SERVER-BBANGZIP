package com.sopt.bbangzip.domain.piece.repository;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {
}
