package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestQueen {
    @Test
    @DisplayName("Initial move set test")
    void initialMoveSet() {
        Board b = new Board();
        Coordinates source = new Coordinates(3, 7);
        Queen q = (Queen) b.getSquare(source).getPiece();
        q.updateLegalPositions(source, b);
        Assertions.assertEquals(0, q.legalPositions.size());
    }

    @Test
    @DisplayName("Complex move set test 1")
    void complexMoveSet1() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 4);
        Queen q = new Queen(Color.WHITE);
        b.getSquare(source).setPiece(q);
        q.updateLegalPositions(source, b);
        Assertions.assertEquals(18, q.legalPositions.size());
    }

    @Test
    @DisplayName("Complex move set test 2")
    void complexMoveSet2() {
        Board b = new Board();
        Coordinates source = new Coordinates(7, 2);
        Queen q = new Queen(Color.WHITE);
        b.getSquare(source).setPiece(q);
        q.updateLegalPositions(source, b);
        Assertions.assertEquals(15, q.legalPositions.size());
    }

    @Test
    @DisplayName("Move set when king is in check")
    void moveSetKingInCheck() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(1, 5)).setPiece(p);
        b.getSquare(new Coordinates(4, 2)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Queen q = new Queen(Color.BLACK);
        b.getSquare(new Coordinates(4, 4)).setPiece(q);
        Coordinates source = new Coordinates(4, 4);
        q.updateAllPositions(source, b);
        Assertions.assertEquals(18, q.legalPositions.size());
        p.updateAllPositions(new Coordinates(1, 5), b);
        q.updateAllPositions(source, b);
        Assertions.assertEquals(3, q.legalPositions.size());
    }

    @Test
    @DisplayName("Move set when king protector")
    void moveSetKingProtector() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(1, 5)).setPiece(p);
        b.getSquare(new Coordinates(4, 2)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Queen q = new Queen(Color.BLACK);
        b.getSquare(new Coordinates(3, 3)).setPiece(q);
        Coordinates source = new Coordinates(3, 3);
        q.updateAllPositions(source, b);
        Assertions.assertEquals(17, q.legalPositions.size());
        p.updateAllPositions(new Coordinates(1, 5), b);
        p.setKingProtectorsInPath(new Coordinates(1, 5), b);
        q.updateAllPositions(source, b);
        Assertions.assertEquals(2, q.legalPositions.size());
    }
}
