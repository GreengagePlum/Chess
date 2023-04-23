package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestKing {

    @Test
    @DisplayName("Invalid move left up")
    void invalidMoveLeftUp() {
        Board b = new Board();
        King k = b.getKing(Color.WHITE);
        Coordinates source = new Coordinates(4, 7);
        Coordinates dest = new Coordinates(4, 6);
        Assertions.assertFalse(k.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move complex")
    void invalidMoveComplex() {
        Board b = new Board();
        King k = b.getKing(Color.WHITE);
        Coordinates source = new Coordinates(4, 7);
        Coordinates dest = new Coordinates(2, 5);
        Assertions.assertFalse(k.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Valid move complex")
    void validMoveComplex() {
        Board b = new Board();
        King k = b.getKing(Color.WHITE);
        Coordinates source = new Coordinates(4, 7);
        Coordinates dest = new Coordinates(5, 6);
        b.getSquare(dest).setPiece(null);
        Assertions.assertTrue(k.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move danger")
    void invalidMoveDanger() {
        Board b = new Board();
        King k = b.getKing(Color.WHITE);
        Coordinates source = new Coordinates(4, 7);
        Coordinates dest = new Coordinates(5, 6);
        b.getSquare(dest).setPiece(null);
        b.getSquare(dest).setDanger(SquareDanger.BLACK_ATTACKING);
        Assertions.assertFalse(k.isLegalPosition(source, dest, b));
    }
}
