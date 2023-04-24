package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestBoard {
    @Test
    @DisplayName("Find piece test")
    void findPieceTest() {
        Board b = new Board();
        Coordinates source = new Coordinates(3, 1);
        Piece p = b.getSquare(source).getPiece();
        Assertions.assertEquals(source.x, b.findPiece(p).x);
        Assertions.assertEquals(source.y, b.findPiece(p).y);
    }
}
