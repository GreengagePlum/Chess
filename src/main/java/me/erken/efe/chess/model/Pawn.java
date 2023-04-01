package me.erken.efe.chess.model;

import java.util.ArrayList;

public final class Pawn extends Piece {

    private boolean firstMove;

    public Pawn(Color color) {
        super(color);
        firstMove = true;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void consumeFirstMove() {
        firstMove = false;
    }

    @Override
    protected boolean isValidMove(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        if (destinationCoords.x >= Board.WIDTH || destinationCoords.x < 0 || destinationCoords.y >= Board.HEIGHT || destinationCoords.y < 0) {
            return false;
        }
        if (sourceCoords.y <= destinationCoords.y || Math.abs(sourceCoords.x - destinationCoords.x) >= 2) {
            return false;
        }
        if (sourceCoords.x == destinationCoords.x && destinationCoords.y >= sourceCoords.y - ((firstMove) ? 2 : 1)) {
            for (int y = sourceCoords.y - 1; y >= destinationCoords.y; y--) {
                if (board.getSquare(new Coordinates(sourceCoords.x, y)).getPiece() != null) {
                    return false;
                }
            }
        } else if (Math.abs(sourceCoords.x - destinationCoords.x) == 1 && Math.abs(sourceCoords.y - destinationCoords.y) == 1) {
            Piece p = board.getSquare(destinationCoords).getPiece();
            if (p == null || p.getColor() == this.getColor()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    protected Coordinates[] movablePositions(Coordinates sourceCoords, Board board) {
        Coordinates[] result = new Coordinates[4];
        int index = 0;
        if (isValidMove(sourceCoords, new Coordinates(sourceCoords.x - 1, sourceCoords.y - 1), board)) {
            result[index] = new Coordinates(sourceCoords.x - 1, sourceCoords.y - 1);
            index++;
        }
        if (isValidMove(sourceCoords, new Coordinates(sourceCoords.x + 1, sourceCoords.y - 1), board)) {
            result[index] = new Coordinates(sourceCoords.x + 1, sourceCoords.y - 1);
            index++;
        }
        if (isValidMove(sourceCoords, new Coordinates(sourceCoords.x, sourceCoords.y - 1), board)) {
            result[index] = new Coordinates(sourceCoords.x, sourceCoords.y - 1);
            index++;
        }
        if (isValidMove(sourceCoords, new Coordinates(sourceCoords.x, sourceCoords.y - 2), board)) {
            result[index] = new Coordinates(sourceCoords.x, sourceCoords.y - 2);
        }
        return result;
    }
}
