package me.erken.efe.chess.model;

public final class Bishop extends Piece {

    public Bishop(Color color) {
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
        if (Math.abs(destinationCoords.x - sourceCoords.x) == Math.abs(destinationCoords.y - sourceCoords.y)) {
            int yEvolution = ((destinationCoords.y - sourceCoords.y < 0) ? -1 : 1);
            int xEvolution = ((destinationCoords.x - sourceCoords.x < 0) ? -1 : 1);
            int x = sourceCoords.x + xEvolution;
            int y = sourceCoords.y + yEvolution;
            while (x != destinationCoords.x && y != destinationCoords.y) {
                Piece p = board.getSquare(new Coordinates(x, y)).getPiece();
                if (p != null) {
                    return false;
                }
                x += xEvolution;
                y += yEvolution;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected Coordinates[] movablePositions(Coordinates sourceCoords, Board board) {
        Coordinates[] result = new Coordinates[13];
        int index = 0;
        int x = sourceCoords.x - 1;
        int y = sourceCoords.y - 1;
        while (isValidMove(sourceCoords, new Coordinates(x, y), board)) {
            result[index].x = x;
            result[index].y = y;
            index++;
            x--;
            y--;
        }
        x = sourceCoords.x - 1;
        y = sourceCoords.y + 1;
        while (isValidMove(sourceCoords, new Coordinates(x, y), board)) {
            result[index].x = x;
            result[index].y = y;
            index++;
            x--;
            y++;
        }
        x = sourceCoords.x + 1;
        y = sourceCoords.y + 1;
        while (isValidMove(sourceCoords, new Coordinates(x, y), board)) {
            result[index].x = x;
            result[index].y = y;
            index++;
            x++;
            y++;
        }
        x = sourceCoords.x + 1;
        y = sourceCoords.y - 1;
        while (isValidMove(sourceCoords, new Coordinates(x, y), board)) {
            result[index].x = x;
            result[index].y = y;
            index++;
            x++;
            y--;
        }
        return result;
    }
}
