package com.sopt.bbangzip.domain.piece.repository;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PieceRepository extends JpaRepository<Piece, Long> {
}
