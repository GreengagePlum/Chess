package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestPawn {

    @Test
    @DisplayName("Constructor Test")
    void constructorTest() {
        Pawn p = new Pawn(Color.WHITE);
        Assertions.assertEquals(Color.WHITE, p.getColor());
        Assertions.assertTrue(p.isFirstMove());
    }

    @Test
    @DisplayName("Consume first move")
    void consumeFirstMove() {
        Pawn p = new Pawn(Color.WHITE);
        p.consumeFirstMove();
        Assertions.assertFalse(p.isFirstMove());
    }

    @Test
    @DisplayName("Valid move one square ahead start of game")
    void validMoveOneAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertTrue(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 5), b));
    }

    @Test
    @DisplayName("Valid move two squares ahead start of game")
    void validMoveTwoAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertTrue(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 4), b));
    }

    @Test
    @DisplayName("Valid move one square ahead after start")
    void validMoveOneAheadAfter() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        p.consumeFirstMove();
        Assertions.assertTrue(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 5), b));
    }

    @Test
    @DisplayName("Invalid move two squares ahead after start")
    void invalidMoveTwoAheadAfter() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        p.consumeFirstMove();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 4), b));
    }

    @Test
    @DisplayName("Valid move diagonal left")
    void validMoveDiagLeft() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(3, 5)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertTrue(p.isValidMove(new Coordinates(4, 6), new Coordinates(3, 5), b));
    }

    @Test
    @DisplayName("Valid move diagonal right")
    void validMoveDiagRight() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(5, 5)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertTrue(p.isValidMove(new Coordinates(4, 6), new Coordinates(5, 5), b));
    }

    @Test
    @DisplayName("Invalid move diagonal left")
    void invalidMoveDiagLeft() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(3, 5), b));
    }

    @Test
    @DisplayName("Invalid move diagonal right")
    void invalidMoveDiagRight() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(5, 5), b));
    }

    @Test
    @DisplayName("Invalid move one square ahead start of game")
    void invalidMoveOneAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(4, 5)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 5), b));
    }

    @Test
    @DisplayName("Invalid move two squares ahead start of game")
    void invalidMoveTwoAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(4, 4)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 4), b));
    }

    @Test
    @DisplayName("Invalid move two squares with blocking piece")
    void invalidMoveTwoAheadBlocked() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(4, 5)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 4), b));
    }

    @Test
    @DisplayName("Invalid move same position")
    void invalidMoveSamePos() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 6), b));
    }

    @Test
    @DisplayName("Invalid move behind")
    void invalidMoveBehind() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(7, 7), b));
    }

    @Test
    @DisplayName("Invalid move too far ahead")
    void invalidMoveFarAhead() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(4, 0), b));
    }

    @Test
    @DisplayName("Invalid move too far diagonal")
    void invalidMoveFarDiag() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(3, 4), b));
    }

    @Test
    @DisplayName("Invalid move sideways")
    void invalidMoveSide() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isValidMove(new Coordinates(4, 6), new Coordinates(3, 6), b));
    }

    @Test
    @DisplayName("Initial movable positions")
    void movablePosInitial() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Coordinates[] cor = p.movablePositions(new Coordinates(4, 6), b);
        int numOfCoords = 0;
        for (int i = 0; i < cor.length; i++) {
            if (cor[i] != null) {
                numOfCoords++;
            }
        }
        Assertions.assertEquals(2, numOfCoords);
        Assertions.assertEquals(5, cor[0].y);
        Assertions.assertEquals(4, cor[1].y);
    }

    @Test
    @DisplayName("Complex movable positions")
    void movablePosComplex() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(3, 5)).setPiece(new Pawn(Color.BLACK));
        b.getSquare(new Coordinates(5, 5)).setPiece(new Pawn(Color.WHITE));
        b.getSquare(new Coordinates(4, 4)).setPiece(new Pawn(Color.WHITE));
        Coordinates[] cor = p.movablePositions(new Coordinates(4, 6), b);
        int numOfCoords = 0;
        for (int i = 0; i < cor.length; i++) {
            if (cor[i] != null) {
                numOfCoords++;
            }
        }
        Assertions.assertEquals(2, numOfCoords);
        Assertions.assertEquals(5, cor[0].y);
        Assertions.assertEquals(5, cor[1].y);
    }
}
