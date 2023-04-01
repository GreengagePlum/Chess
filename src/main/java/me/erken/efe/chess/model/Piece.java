package me.erken.efe.chess.model;

public abstract class Piece {
    private static int idCounter = 0;
    private final int id;
    private final Color color;

    public Piece(Color color) {
        this.color = color;
        id = idCounter;
        idCounter++;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    protected abstract boolean isValidMove(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    protected abstract Coordinates[] movablePositions(Coordinates sourceCoords, Board board);

}
