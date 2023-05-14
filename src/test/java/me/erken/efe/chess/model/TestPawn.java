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
        MoveHistory history = new MoveHistory();
        Assertions.assertTrue(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 5), b, history));
    }

    @Test
    @DisplayName("Valid move two squares ahead start of game")
    void validMoveTwoAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        MoveHistory history = new MoveHistory();
        Assertions.assertTrue(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 4), b, history));
    }

    @Test
    @DisplayName("Valid move one square ahead after start")
    void validMoveOneAheadAfter() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        p.consumeFirstMove();
        MoveHistory history = new MoveHistory();
        Assertions.assertTrue(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 5), b, history));
    }

    @Test
    @DisplayName("Invalid move two squares ahead after start")
    void invalidMoveTwoAheadAfter() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        p.consumeFirstMove();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 4), b));
    }

    @Test
    @DisplayName("Valid move diagonal left")
    void validMoveDiagLeft() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(3, 5)).setPiece(new Pawn(Color.BLACK));
        MoveHistory history = new MoveHistory();
        Assertions.assertTrue(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(3, 5), b, history));
    }

    @Test
    @DisplayName("Valid move diagonal right")
    void validMoveDiagRight() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(5, 5)).setPiece(new Pawn(Color.BLACK));
        MoveHistory history = new MoveHistory();
        Assertions.assertTrue(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(5, 5), b, history));
    }

    @Test
    @DisplayName("Invalid move diagonal left")
    void invalidMoveDiagLeft() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(3, 5), b));
    }

    @Test
    @DisplayName("Invalid move diagonal right")
    void invalidMoveDiagRight() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(5, 5), b));
    }

    @Test
    @DisplayName("Invalid move one square ahead start of game")
    void invalidMoveOneAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(4, 5)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 5), b));
    }

    @Test
    @DisplayName("Invalid move two squares ahead start of game")
    void invalidMoveTwoAheadStart() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(4, 4)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 4), b));
    }

    @Test
    @DisplayName("Invalid move two squares with blocking piece")
    void invalidMoveTwoAheadBlocked() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(4, 5)).setPiece(new Pawn(Color.BLACK));
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 4), b));
    }

    @Test
    @DisplayName("Invalid move same position")
    void invalidMoveSamePos() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 6), b));
    }

    @Test
    @DisplayName("Invalid move behind")
    void invalidMoveBehind() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(7, 7), b));
    }

    @Test
    @DisplayName("Invalid move too far ahead")
    void invalidMoveFarAhead() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(4, 0), b));
    }

    @Test
    @DisplayName("Invalid move too far diagonal")
    void invalidMoveFarDiag() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(3, 4), b));
    }

    @Test
    @DisplayName("Invalid move sideways")
    void invalidMoveSide() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        Assertions.assertFalse(p.isLegalPosition(new Coordinates(4, 6), new Coordinates(3, 6), b));
    }

    @Test
    @DisplayName("Initial movable positions")
    void movablePosInitial() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        MoveHistory history = new MoveHistory();
        p.updateLegalPositions(new Coordinates(4, 6), b, history);
        Assertions.assertEquals(2, p.legalPositions.size());
        Assertions.assertEquals(5, p.legalPositions.get(0).y);
        Assertions.assertEquals(4, p.legalPositions.get(1).y);
    }

    @Test
    @DisplayName("Complex movable positions")
    void movablePosComplex() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(4, 6)).getPiece();
        b.getSquare(new Coordinates(3, 5)).setPiece(new Pawn(Color.BLACK));
        b.getSquare(new Coordinates(5, 5)).setPiece(new Pawn(Color.WHITE));
        b.getSquare(new Coordinates(4, 4)).setPiece(new Pawn(Color.WHITE));
        MoveHistory history = new MoveHistory();
        p.updateLegalPositions(new Coordinates(4, 6), b, history);
        Assertions.assertEquals(2, p.legalPositions.size());
        Assertions.assertEquals(5, p.legalPositions.get(0).y);
        Assertions.assertEquals(5, p.legalPositions.get(1).y);
    }

    @Test
    @DisplayName("Set opposite king to Check")
    void setOppositeKingToCheck() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(2, 6)).getPiece();
        b.getSquare(new Coordinates(3, 5)).setPiece(b.getKing(Color.BLACK));
        b.getSquare(new Coordinates(4, 0)).setPiece(null);
        Coordinates source = new Coordinates(2, 6);
        Assertions.assertFalse(b.getKing(Color.BLACK).isInCheck());
        MoveHistory history = new MoveHistory();
        p.updateAllPositions(source, b, history);
        Assertions.assertTrue(b.getKing(Color.BLACK).isInCheck());
    }

    @Test
    @DisplayName("Move set when king is in check")
    void moveSetKingInCheck() {
        Board b = new Board();
        Pawn p = (Pawn) b.getSquare(new Coordinates(2, 6)).getPiece();
        b.getSquare(new Coordinates(1, 5)).setPiece(b.getKing(Color.WHITE));
        b.getSquare(new Coordinates(4, 7)).setPiece(null);
        Bishop p2 = new Bishop(Color.BLACK);
        b.getSquare(new Coordinates(4, 2)).setPiece(p2);
        MoveHistory history = new MoveHistory();
        Coordinates source = new Coordinates(2, 6);
        p.updateAllPositions(source, b, history);
        Assertions.assertEquals(2, p.legalPositions.size());
        p2.updateAllPositions(new Coordinates(4, 2), b);
        p.updateAllPositions(source, b, history);
        Assertions.assertEquals(1, p.legalPositions.size());
    }

    @Test
    @DisplayName("Move set when king protector")
    void moveSetKingProtector() {
        Board b = new Board();
        Pawn p = new Pawn(Color.WHITE);
        b.getSquare(new Coordinates(3, 3)).setPiece(p);
        b.getSquare(new Coordinates(1, 5)).setPiece(b.getKing(Color.WHITE));
        b.getSquare(new Coordinates(4, 7)).setPiece(null);
        Bishop p2 = new Bishop(Color.BLACK);
        b.getSquare(new Coordinates(4, 2)).setPiece(p2);
        MoveHistory history = new MoveHistory();
        Coordinates source = new Coordinates(3, 3);
        p.updateAllPositions(source, b, history);
        Assertions.assertEquals(2, p.legalPositions.size());
        p2.updateAllPositions(new Coordinates(4, 2), b);
        p2.setKingProtectorsInPath(new Coordinates(4, 2), b);
        p.updateAllPositions(source, b, history);
        Assertions.assertEquals(1, p.legalPositions.size());
    }


}
