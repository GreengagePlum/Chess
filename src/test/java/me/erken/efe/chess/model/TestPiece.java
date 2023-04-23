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
            protected boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
                return false;
            }

            @Override
            protected boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
                return false;
            }

            @Override
            protected void updateLegalPositions(Coordinates sourceCoords, Board board) {
            }

            @Override
            protected void updateAttackingPositions(Coordinates sourceCoords, Board board) {

            }

            @Override
            protected void updateAllPositions(Coordinates sourceCoords, Board board) {

            }

            @Override
            protected void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {

            }

            @Override
            protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
                return false;
            }
        };
        Assertions.assertEquals(Color.BLACK, p.getColor());
        p = new Piece(Color.WHITE) {
            @Override
            protected boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
                return false;
            }

            @Override
            protected boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
                return false;
            }

            @Override
            protected void updateLegalPositions(Coordinates sourceCoords, Board board) {
            }

            @Override
            protected void updateAttackingPositions(Coordinates sourceCoords, Board board) {

            }

            @Override
            protected void updateAllPositions(Coordinates sourceCoords, Board board) {

            }

            @Override
            protected void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {

            }

            @Override
            protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
                return false;
            }
        };
        Assertions.assertEquals(Color.WHITE, p.getColor());
    }
}
