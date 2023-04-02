package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestPiece {

    @Test
    @DisplayName("Constructor test")
    void constructorTest() {
        Piece p = new Piece(Color.BLACK) {
            @Override
            protected boolean isValidMove(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
                return false;
            }

            @Override
            protected Coordinates[] movablePositions(Coordinates sourceCoords, Board board) {
                return new Coordinates[0];
            }
        };
        Assertions.assertEquals(Color.BLACK, p.getColor());
        p = new Piece(Color.WHITE) {
            @Override
            protected boolean isValidMove(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
                return false;
            }

            @Override
            protected Coordinates[] movablePositions(Coordinates sourceCoords, Board board) {
                return new Coordinates[0];
            }
        };
        Assertions.assertEquals(Color.WHITE, p.getColor());
    }
}
