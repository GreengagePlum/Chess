package me.erken.efe.chess.model;

public final class Knight extends Piece {
    public Knight(Color color) {
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
        return (destinationCoords.y == sourceCoords.y - 2 && destinationCoords.x == sourceCoords.x - 1)
                || (destinationCoords.y == sourceCoords.y - 1 && destinationCoords.x == sourceCoords.x - 2)
                || (destinationCoords.y == sourceCoords.y + 1 && destinationCoords.x == sourceCoords.x - 2)
                || (destinationCoords.y == sourceCoords.y + 2 && destinationCoords.x == sourceCoords.x - 1)
                || (destinationCoords.y == sourceCoords.y + 2 && destinationCoords.x == sourceCoords.x + 1)
                || (destinationCoords.y == sourceCoords.y + 1 && destinationCoords.x == sourceCoords.x + 2)
                || (destinationCoords.y == sourceCoords.y - 1 && destinationCoords.x == sourceCoords.x + 2)
                || (destinationCoords.y == sourceCoords.y - 2 && destinationCoords.x == sourceCoords.x + 1);
    }

    @Override
    protected Coordinates[] movablePositions(Coordinates sourceCoords, Board board) {
        Coordinates[] result = new Coordinates[8];
        int index = 0;
        Coordinates tmp = new Coordinates(sourceCoords.x - 1, sourceCoords.y - 2);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
            index++;
        }
        tmp = new Coordinates(sourceCoords.x - 2, sourceCoords.y - 1);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
            index++;
        }
        tmp = new Coordinates(sourceCoords.x - 2, sourceCoords.y + 1);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
            index++;
        }
        tmp = new Coordinates(sourceCoords.x - 1, sourceCoords.y + 2);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
            index++;
        }
        tmp = new Coordinates(sourceCoords.x + 1, sourceCoords.y + 2);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
            index++;
        }
        tmp = new Coordinates(sourceCoords.x + 2, sourceCoords.y + 1);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
            index++;
        }
        tmp = new Coordinates(sourceCoords.x + 2, sourceCoords.y - 1);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
            index++;
        }
        tmp = new Coordinates(sourceCoords.x + 1, sourceCoords.y - 2);
        if (isValidMove(sourceCoords, tmp, board)) {
            result[index] = tmp;
        }
        return result;
    }
}
