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
        Coordinates[] moves = r.movablePositions(source, b);
        int length = 0;
        for (; length < moves.length && moves[length] != null; length++) ;
        Assertions.assertEquals(0, length);
    }

    @Test
    @DisplayName("Complex move set test 1")
    void complexMoveSet1() {
        Board b = new Board();
        Coordinates source = new Coordinates(2, 4);
        Rook r = new Rook(Color.WHITE);
        b.getSquare(source).setPiece(r);
        Coordinates[] moves = r.movablePositions(source, b);
        int length = 0;
        for (; length < moves.length && moves[length] != null; length++) ;
        Assertions.assertEquals(11, length);
    }

    @Test
    @DisplayName("Complex move set test 2")
    void complexMoveSet2() {
        Board b = new Board();
        Coordinates source = new Coordinates(7, 2);
        Rook r = new Rook(Color.WHITE);
        b.getSquare(source).setPiece(r);
        Coordinates[] moves = r.movablePositions(source, b);
        int length = 0;
        for (; length < moves.length && moves[length] != null; length++) ;
        Assertions.assertEquals(11, length);
    }
}
