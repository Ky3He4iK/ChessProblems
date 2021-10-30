package dev.ky3he4ik.chessproblems.presentation.view.chess.utils

import dev.ky3he4ik.chessproblems.R

enum class Piece(val id: Int, val letter: Char?, val isWhite: Boolean) {
    WhiteKing(R.drawable.piece_white_king, 'K', true),
    WhiteQueen(R.drawable.piece_white_queen, 'Q', true),
    WhiteBishop(R.drawable.piece_white_bishop, 'B', true),
    WhiteKnight(R.drawable.piece_white_knight, 'N', true),
    WhiteRook(R.drawable.piece_white_rook, 'R', true),
    WhitePawn(R.drawable.piece_white_pawn, null, true),
    BlackKing(R.drawable.piece_black_king, 'K', false),
    BlackQueen(R.drawable.piece_black_queen, 'Q', false),
    BlackBishop(R.drawable.piece_black_bishop, 'B', false),
    BlackKnight(R.drawable.piece_black_knight, 'N', false),
    BlackRook(R.drawable.piece_black_rook, 'R', false),
    BlackPawn(R.drawable.piece_black_pawn, null, false),
}
