package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestGame {
    @Test
    @DisplayName("Complex situation test")
    void complexSituationTest() {
        Board b = new Board();
        b.getSquare(new Coordinates(0, 7)).setPiece(null);
        b.getSquare(new Coordinates(2, 7)).setPiece(null);
        b.getSquare(new Coordinates(0, 6)).setPiece(null);
        b.getSquare(new Coordinates(2, 6)).setPiece(null);
        b.getSquare(new Coordinates(3, 6)).setPiece(null);
        b.getSquare(new Coordinates(4, 6)).setPiece(null);
        b.getSquare(new Coordinates(7, 6)).setPiece(null);
        Rook BR1 = new Rook(Color.BLACK);
        b.getSquare(new Coordinates(1, 6)).setPiece(BR1);
        Queen BQ = new Queen(Color.BLACK);
        b.getSquare(new Coordinates(5, 6)).setPiece(BQ);
        Knight BK1 = new Knight(Color.BLACK);
        b.getSquare(new Coordinates(4, 4)).setPiece(BK1);
        Coordinates source = new Coordinates(5, 7);
        Bishop WB1 = (Bishop) b.getSquare(source).getPiece();
        WB1.updateLegalPositions(source, b);
        Assertions.assertEquals(5, WB1.legalPositionsCount());
        // a part of the game logic
        b.clearKingsCheck();
        b.clearStateSquares();
        b.clearDangerSquares();
        MoveHistory mh = new MoveHistory();
        b.calculateAllPieces(Color.BLACK, mh);
        Assertions.assertEquals(0, WB1.legalPositionsCount());
    }

    @Test
    @DisplayName("Complex situation test 2")
    void complexSituationTest2() {
        Board b = new Board();
        b.getSquare(new Coordinates(0, 7)).setPiece(null);
        b.getSquare(new Coordinates(2, 7)).setPiece(null);
        b.getSquare(new Coordinates(0, 6)).setPiece(null);
        b.getSquare(new Coordinates(2, 6)).setPiece(null);
        b.getSquare(new Coordinates(3, 6)).setPiece(null);
        b.getSquare(new Coordinates(4, 6)).setPiece(null);
        b.getSquare(new Coordinates(7, 6)).setPiece(null);
        Rook BR1 = new Rook(Color.BLACK);
        b.getSquare(new Coordinates(1, 6)).setPiece(BR1);
        Queen BQ = new Queen(Color.BLACK);
        b.getSquare(new Coordinates(5, 6)).setPiece(BQ);
        Knight BK1 = new Knight(Color.BLACK);
        b.getSquare(new Coordinates(4, 4)).setPiece(BK1);
        Coordinates source = new Coordinates(4, 7);
        King WK = b.getKing(Color.WHITE);
        WK.updateLegalPositions(source, b);
        Assertions.assertEquals(3, WK.legalPositionsCount());
        // a part of the game logic
        b.clearKingsCheck();
        b.clearStateSquares();
        b.clearDangerSquares();
        MoveHistory mh = new MoveHistory();
        b.calculateAllPieces(Color.BLACK, mh);
        Assertions.assertEquals(0, WK.legalPositionsCount());
    }

    @Test
    @DisplayName("Complex situation test 3")
    void complexSituationTest3() {
        Board b = new Board();
        b.getSquare(new Coordinates(3, 0)).setPiece(null);
        b.getSquare(new Coordinates(5, 1)).setPiece(null);
        b.getSquare(new Coordinates(2, 1)).setPiece(null);
        Queen WQ = new Queen(Color.WHITE);
        Coordinates sourceWQ = new Coordinates(3, 1);
        b.getSquare(sourceWQ).setPiece(WQ);
        King BK = b.getKing(Color.BLACK);
        Coordinates sourceBK = b.findPiece(BK);
        BK.updateLegalPositions(sourceBK, b);
        Assertions.assertEquals(3, BK.legalPositionsCount());
        // a part of the game logic
        b.clearKingsCheck();
        b.clearStateSquares();
        b.clearDangerSquares();
        b.clearPieceKingProtectors();
        MoveHistory mh = new MoveHistory();
        b.calculateAllPieces(Color.WHITE, mh);
        Assertions.assertEquals(2, BK.legalPositionsCount());
        Player p2 = new Player(Color.BLACK);
        p2.makeSelection(b.getSquare(sourceBK), b);
        Assertions.assertEquals(SquareState.HIGHLIGHTED, b.getSquare(sourceWQ).getState());
    }
}
