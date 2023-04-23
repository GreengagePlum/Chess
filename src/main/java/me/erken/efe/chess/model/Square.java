package me.erken.efe.chess.model;

public class Square {
    private Piece piece;
    private SquareState state;
    private SquareDanger danger;

    public Square() {
        this(null, SquareState.NORMAL, SquareDanger.PEACEFUL);
    }

    public Square(Piece piece, SquareState state, SquareDanger danger) {
        this.piece = piece;
        this.state = state;
        this.danger = danger;
    }

    public Piece getPiece() {
        return piece;
    }

    public SquareState getState() {
        return state;
    }

    public SquareDanger getDanger() {
        return danger;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setState(SquareState state) {
        this.state = state;
    }

    public void setDanger(SquareDanger danger) {
        this.danger = danger;
    }
}
