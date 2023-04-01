package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestPiece {
    private Piece p = new Piece(Color.BLACK, 2, 5) {
        @Override
        protected boolean isValidMove(int positionX, int positionY, Board board) {
            return false;
        }
    };

    @Test
    @DisplayName("Constructor test")
    void constructorTest() {
        Piece p = new Piece(Color.BLACK, 2, 5) {
            @Override
            protected boolean isValidMove(int positionX, int positionY, Board board) {
                return false;
            }
        };
        Assertions.assertEquals(Color.BLACK, p.getColor());
        Assertions.assertEquals(2, p.getPositionX());
        Assertions.assertEquals(5, p.getPositionY());
        p = new Piece(Color.WHITE, 2, 5) {
            @Override
            protected boolean isValidMove(int positionX, int positionY, Board board) {
                return false;
            }
        };
        Assertions.assertEquals(Color.WHITE, p.getColor());
    }

    @Test
    @DisplayName("Setter Test")
    void setterTest() {
        p.setPositionX(4);
        Assertions.assertEquals(4, p.getPositionX());
        p.setPositionY(9);
        Assertions.assertEquals(9, p.getPositionY());
        p.setPosition(3, 1);
        Assertions.assertEquals(3, p.getPositionX());
        Assertions.assertEquals(1, p.getPositionY());
    }

    @Test
    @Disabled("Mockup phase, subject to changes")
    @DisplayName("Display test")
    void printTest() {
        p.setPosition(3, 5);
        Assertions.assertEquals("Pièce aux coordonnées : (3, 5)", p.toString());
    }
}
