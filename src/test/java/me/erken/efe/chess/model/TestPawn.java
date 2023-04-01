package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestPawn {

    @Test
    @DisplayName("Constructor Test")
    void constructorTest() {
        Pawn p = new Pawn(Color.WHITE, 3, 5);
        Assertions.assertEquals(Color.WHITE, p.getColor());
        Assertions.assertTrue(p.isFirstMove());
    }

    @Test
    @DisplayName("Consume first move")
    void consumeFirstMove() {
        Pawn p = new Pawn(Color.WHITE, 3, 5);
        p.consumeFirstMove();
        Assertions.assertFalse(p.isFirstMove());
    }

    @Test
    @DisplayName("Valid move one square ahead start of game")
    void validMoveOneAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertTrue(p.isValidMove(4, 5, b));
    }

    @Test
    @DisplayName("Valid move two squares ahead start of game")
    void validMoveTwoAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertTrue(p.isValidMove(4, 4, b));
    }

    @Test
    @DisplayName("Valid move one square ahead after start")
    void validMoveOneAheadAfter() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        p.consumeFirstMove();
        Assertions.assertTrue(p.isValidMove(4, 5, b));
    }

    @Test
    @DisplayName("Invalid move two squares ahead after start")
    void invalidMoveTwoAheadAfter() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        p.consumeFirstMove();
        Assertions.assertFalse(p.isValidMove(4, 4, b));
    }

    @Test
    @DisplayName("Valid move diagonal left")
    void validMoveDiagLeft() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        b.getSquare(3, 5).setPiece(new Pawn(Color.BLACK, 3, 5));
        Assertions.assertTrue(p.isValidMove(3, 5, b));
    }

    @Test
    @DisplayName("Valid move diagonal right")
    void validMoveDiagRight() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        b.getSquare(5, 5).setPiece(new Pawn(Color.BLACK, 3, 5));
        Assertions.assertTrue(p.isValidMove(5, 5, b));
    }

    @Test
    @DisplayName("Invalid move diagonal left")
    void invalidMoveDiagLeft() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertFalse(p.isValidMove(3, 5, b));
    }

    @Test
    @DisplayName("Invalid move diagonal right")
    void invalidMoveDiagRight() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertFalse(p.isValidMove(5, 5, b));
    }

    @Test
    @DisplayName("Invalid move one square ahead start of game")
    void invalidMoveOneAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        b.getSquare(4, 5).setPiece(new Pawn(Color.BLACK, 3, 5));
        Assertions.assertFalse(p.isValidMove(4, 5, b));
    }

    @Test
    @DisplayName("Invalid move two squares ahead start of game")
    void invalidMoveTwoAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        b.getSquare(4, 4).setPiece(new Pawn(Color.BLACK, 3, 5));
        Assertions.assertFalse(p.isValidMove(4, 4, b));
    }

    @Test
    @DisplayName("Invalid move two squares with blocking piece")
    void invalidMoveTwoAheadBlocked() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        b.getSquare(4, 5).setPiece(new Pawn(Color.BLACK, 3, 5));
        Assertions.assertFalse(p.isValidMove(4, 4, b));
    }

    @Test
    @DisplayName("Invalid move same position")
    void invalidMoveSamePos() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertFalse(p.isValidMove(4, 6, b));
    }

    @Test
    @DisplayName("Invalid move behind")
    void invalidMoveBehind() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertFalse(p.isValidMove(7, 7, b));
    }

    @Test
    @DisplayName("Invalid move too far ahead")
    void invalidMoveFarAhead() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertFalse(p.isValidMove(4, 0, b));
    }

    @Test
    @DisplayName("Invalid move too far diagonal")
    void invalidMoveFarDiag() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertFalse(p.isValidMove(3, 4, b));
    }

    @Test
    @DisplayName("Invalid move sideways")
    void invalidMoveSide() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(4, 6).getPiece();
        Assertions.assertFalse(p.isValidMove(3, 6, b));
    }
}
