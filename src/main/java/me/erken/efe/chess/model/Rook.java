package me.erken.efe.chess.model;

public final class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    @Override
    protected boolean isValidMove(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        if (!coordCheck(destinationCoords)) {
            return false;
        }
        Piece destPiece = board.getSquare(destinationCoords).getPiece();
        if (destPiece != null && destPiece.getColor() == this.getColor()) {
            return false;
        }
        if (destinationCoords.x == sourceCoords.x && destinationCoords.y != sourceCoords.y) {
            int yEvolution = ((destinationCoords.y - sourceCoords.y < 0) ? -1 : 1);
            for (int y = sourceCoords.y + yEvolution; y != destinationCoords.y; y += yEvolution) {
                if (board.getSquare(new Coordinates(sourceCoords.x, y)).getPiece() != null) {
                    return false;
                }
            }
        } else if (destinationCoords.x != sourceCoords.x && destinationCoords.y == sourceCoords.y) {
            int xEvolution = ((destinationCoords.x - sourceCoords.x < 0) ? -1 : 1);
            for (int x = sourceCoords.x + xEvolution; x != destinationCoords.x; x += xEvolution) {
                if (board.getSquare(new Coordinates(x, sourceCoords.y)).getPiece() != null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected Coordinates[] movablePositions(Coordinates sourceCoords, Board board) {
        Coordinates[] result = new Coordinates[14];
        int index = 0;
        for (int y = 0; y < Board.HEIGHT; y++) {
            if (y != sourceCoords.y) {
                Coordinates tmp = new Coordinates(sourceCoords.x, y);
                if (isValidMove(sourceCoords, tmp, board)) {
                    result[index] = tmp;
                    index++;
                }
            }
        }
        for (int x = 0; x < Board.WIDTH; x++) {
            if (x != sourceCoords.x) {
                Coordinates tmp = new Coordinates(x, sourceCoords.y);
                if (isValidMove(sourceCoords, tmp, board)) {
                    result[index] = tmp;
                    index++;
                }
            }
        }
        return result;
    }
}
