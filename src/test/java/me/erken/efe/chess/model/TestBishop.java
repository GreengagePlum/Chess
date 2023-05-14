package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestBishop {
    @Test
    @DisplayName("Invalid move left up")
    void invalidMoveLeftUp() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(1, 6);
        Bishop p = (Bishop) b.getSquare(source).getPiece();
        Assertions.assertFalse(p.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move left bottom")
    void invalidMoveLeftBottom() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(1, 8);
        Bishop p = (Bishop) b.getSquare(source).getPiece();
        Assertions.assertFalse(p.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move right bottom")
    void invalidMoveRightBottom() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(3, 8);
        Bishop p = (Bishop) b.getSquare(source).getPiece();
        Assertions.assertFalse(p.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move right up")
    void invalidMoveRightUp() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(3, 6);
        Bishop p = (Bishop) b.getSquare(source).getPiece();
        Assertions.assertFalse(p.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move right up far")
    void invalidMoveRightUpFar() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 7);
        Coordinates dest = new Coordinates(5, 4);
        Bishop p = (Bishop) b.getSquare(source).getPiece();
        Assertions.assertFalse(p.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Invalid move complex")
    void invalidMoveComplex() {
        Board b = new Board();
        Coordinates source = new Coordinates(3, 4);
        Coordinates dest = new Coordinates(1, 6);
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(source).setPiece(p);
        Assertions.assertFalse(p.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Valid move complex")
    void validMoveComplex() {
        Board b = new Board();
        Coordinates source = new Coordinates(3, 4);
        Coordinates dest = new Coordinates(6, 1);
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(source).setPiece(p);
        Assertions.assertTrue(p.isLegalPosition(source, dest, b));
    }

    @Test
    @DisplayName("Valid king protector")
    void validKingProtector() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        Coordinates source = new Coordinates(1, 5);
        b.getSquare(source).setPiece(p);
        Pawn p2 = new Pawn(Color.BLACK);
        b.getSquare(new Coordinates(2, 4)).setPiece(p2);
        King k = new King(Color.BLACK);
        b.getSquare(new Coordinates(4, 2)).setPiece(k);
        Assertions.assertFalse(p2.isRoyalProtector());
        p.updateAllPositions(source, b);
        p.setKingProtectorsInPath(source, b);
        Assertions.assertTrue(p2.isRoyalProtector());
    }

    @Test
    @DisplayName("Set opposite king to Check")
    void setOppositeKingToCheck() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        Coordinates source = new Coordinates(1, 5);
        b.getSquare(source).setPiece(p);
        b.getSquare(new Coordinates(4, 2)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Assertions.assertFalse(b.getKing(Color.BLACK).isInCheck());
        p.updateAllPositions(source, b);
        Assertions.assertTrue(b.getKing(Color.BLACK).isInCheck());
    }

    @Test
    @DisplayName("Move set when king is in check")
    void moveSetKingInCheck() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(1, 5)).setPiece(p);
        b.getSquare(new Coordinates(4, 2)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Bishop p2 = new Bishop(Color.BLACK);
        Coordinates source = new Coordinates(4, 4);
        b.getSquare(source).setPiece(p2);
        p2.updateAllPositions(source, b);
        Assertions.assertEquals(8, p2.legalPositions.size());
        p.updateAllPositions(new Coordinates(1, 5), b);
        p2.updateAllPositions(source, b);
        Assertions.assertEquals(1, p2.legalPositions.size());
    }

    @Test
    @DisplayName("Move set when king protector")
    void moveSetKingProtector() {
        Board b = new Board();
        Bishop p = new Bishop(Color.WHITE);
        b.getSquare(new Coordinates(1, 5)).setPiece(p);
        b.getSquare(new Coordinates(4, 2)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Bishop p2 = new Bishop(Color.BLACK);
        b.getSquare(new Coordinates(3, 3)).setPiece(p2);
        Coordinates source = new Coordinates(3, 3);
        p2.updateAllPositions(source, b);
        Assertions.assertEquals(6, p2.legalPositions.size());
        p.updateAllPositions(new Coordinates(1, 5), b);
        p.setKingProtectorsInPath(new Coordinates(1, 5), b);
        p2.updateAllPositions(source, b);
        Assertions.assertEquals(2, p2.legalPositions.size());
    }
}
