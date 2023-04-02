package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestBishop {
    @Test
    @DisplayName("Invalid move left up")
    void invalidMoveLeftUp() {
        Board b = new Board();
        Bishop p = (Bishop) b.getSquare(new Coordinates(2, 7)).getPiece();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(1, 6);
        Assertions.assertFalse(p.isValidMove(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move left bottom")
    void invalidMoveLeftBottom() {
        Board b = new Board();
        Bishop p = (Bishop) b.getSquare(new Coordinates(2, 7)).getPiece();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(1, 8);
        Assertions.assertFalse(p.isValidMove(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move right bottom")
    void invalidMoveRightBottom() {
        Board b = new Board();
        Bishop p = (Bishop) b.getSquare(new Coordinates(2, 7)).getPiece();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(3, 8);
        Assertions.assertFalse(p.isValidMove(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move right up")
    void invalidMoveRightUp() {
        Board b = new Board();
        Bishop p = (Bishop) b.getSquare(new Coordinates(2, 7)).getPiece();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(3, 6);
        Assertions.assertFalse(p.isValidMove(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move right up far")
    void invalidMoveRightUpFar() {
        Board b = new Board();
        Bishop p = (Bishop) b.getSquare(new Coordinates(2, 7)).getPiece();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(5, 4);
        Assertions.assertFalse(p.isValidMove(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move complex")
    void invalidMoveComplex() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(3, 4)).setPiece(p);
        Coordinates source = new Coordinates(3, 4);
        Coordinates dest = new Coordinates(1, 6);
        Assertions.assertFalse(p.isValidMove(source, dest, b));
    }

    @Test
    @DisplayName("Valid move complex")
    void validMoveComplex() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(3, 4)).setPiece(p);
        Coordinates source = new Coordinates(3, 4);
        Coordinates dest = new Coordinates(6, 1);
        Assertions.assertTrue(p.isValidMove(source, dest, b));
    }
}
