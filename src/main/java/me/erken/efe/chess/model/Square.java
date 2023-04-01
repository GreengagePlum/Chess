package me.erken.efe.chess.model;

public class Square {
    private Piece piece;
    private SquareState state;

    public Square() {
        this(null, SquareState.NORMAL);
    }

    public Square(Piece piece, SquareState state) {
        this.piece = piece;
        this.state = state;
    }

    public Piece getPiece() {
        return piece;
    }

    public SquareState getState() {
        return state;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setState(SquareState state) {
        this.state = state;
    }
}
