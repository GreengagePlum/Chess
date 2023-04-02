package me.erken.efe.chess.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestKnight {
    @Test
    @DisplayName("Initial move set test")
    void initialMoveSet() {
        Board b = new Board();
        Knight k = (Knight) b.getSquare(new Coordinates(1, 7)).getPiece();
        Coordinates source = new Coordinates(1, 7);
        Coordinates[] moves = k.movablePositions(source, b);
        int length = 0;
        for (; moves[length] != null; length++) ;
        Assertions.assertEquals(2, length);
    }

    @Test
    @DisplayName("Complex move set test 1")
    void complexMoveSet1() {
        Board b = new Board();
        Knight k = new Knight(Color.WHITE);
        b.getSquare(new Coordinates(2, 4)).setPiece(k);
        Coordinates source = new Coordinates(2, 4);
        Coordinates[] moves = k.movablePositions(source, b);
        int length = 0;
        for (; length < moves.length && moves[length] != null; length++) ;
        Assertions.assertEquals(6, length);
    }

    @Test
    @DisplayName("Complex move set test 2")
    void complexMoveSet2() {
        Board b = new Board();
        Knight k = new Knight(Color.WHITE);
        b.getSquare(new Coordinates(2, 3)).setPiece(k);
        Coordinates source = new Coordinates(2, 3);
        Coordinates[] moves = k.movablePositions(source, b);
        int length = 0;
        for (; length < moves.length && moves[length] != null; length++) ;
        Assertions.assertEquals(8, length);
    }
}
