package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestRook {
    @Test
    @DisplayName("Initial move set test")
    void initialMoveSet() {
        Board b = new Board();
        Coordinates source = new Coordinates(0, 7);
        Rook r = (Rook) b.getSquare(source).getPiece();
        r.updateLegalPositions(source, b);
        Assertions.assertEquals(0, r.legalPositions.size());
    }

    @Test
    @DisplayName("Complex move set test 1")
    void complexMoveSet1() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 4);
        Rook r = new Rook(Color.WHITE);
        b.getSquare(source).setPiece(r);
        r.updateLegalPositions(source, b);
        Assertions.assertEquals(11, r.legalPositions.size());
    }

    @Test
    @DisplayName("Complex move set test 2")
    void complexMoveSet2() {
        Board b = new Board();
        Coordinates source = new Coordinates(7, 2);
        Rook r = new Rook(Color.WHITE);
        b.getSquare(source).setPiece(r);
        r.updateLegalPositions(source, b);
        Assertions.assertEquals(11, r.legalPositions.size());
    }

    @Test
    @DisplayName("Move set when king is in check")
    void moveSetKingInCheck() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(1, 5)).setPiece(p);
        b.getSquare(new Coordinates(4, 2)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Rook r = new Rook(Color.BLACK);
        b.getSquare(new Coordinates(4, 4)).setPiece(r);
        Coordinates source = new Coordinates(4, 4);
        r.updateAllPositions(source, b);
        Assertions.assertEquals(10, r.legalPositions.size());
        p.updateAllPositions(new Coordinates(1, 5), b);
        r.updateAllPositions(source, b);
        Assertions.assertEquals(2, r.legalPositions.size());
    }

    @Test
    @DisplayName("Move set when king protector")
    void moveSetKingProtector() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(1, 5)).setPiece(p);
        b.getSquare(new Coordinates(4, 2)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Rook r = new Rook(Color.BLACK);
        b.getSquare(new Coordinates(3, 3)).setPiece(r);
        Coordinates source = new Coordinates(3, 3);
        r.updateAllPositions(source, b);
        Assertions.assertEquals(11, r.legalPositions.size());
        p.updateAllPositions(new Coordinates(1, 5), b);
        p.setKingProtectorsInPath(new Coordinates(1, 5), b);
        r.updateAllPositions(source, b);
        Assertions.assertEquals(0, r.legalPositions.size());
    }
}
