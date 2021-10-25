package dev.ky3he4ik.chessproblems.presentation.view.chess

import dev.ky3he4ik.chessproblems.R

enum class Piece(val id: Int, letter: Char?) {
    WhiteKing(R.drawable.piece_white_king, 'K'),
    WhiteQueen(R.drawable.piece_white_queen, 'Q'),
    WhiteBishop(R.drawable.piece_white_bishop, 'B'),
    WhiteKnight(R.drawable.piece_white_knight, 'N'),
    WhiteRook(R.drawable.piece_white_rook, 'R'),
    WhitePawn(R.drawable.piece_white_pawn, null),
    BlackKing(R.drawable.piece_black_king, 'K'),
    BlackQueen(R.drawable.piece_black_queen, 'Q'),
    BlackBishop(R.drawable.piece_black_bishop, 'B'),
    BlackKnight(R.drawable.piece_black_knight, 'N'),
    BlackRook(R.drawable.piece_black_rook, 'R'),
    BlackPawn(R.drawable.piece_black_pawn, null),
}
