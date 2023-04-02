package me.erken.efe.chess.model;

public class Player {
    private static int iDCounter = 0;
    private final Color color;
    private final int id;
    private Square selection;

    public Player(Color color) {
        this.color = color;
        id = iDCounter;
        iDCounter++;
        selection = null;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    private void setSelection(Square square) {
        selection = square;
    }

    private void unsetSelection() {
        selection = null;
    }

    public void makeSelection(Square square, Board board) {
        Piece p = square.getPiece();
        if (p == null || p.getColor() != color) {
            board.clearSquares();
            this.unsetSelection();
        } else {
            board.selectSquare(square);
            this.setSelection(square);
        }
    }

    public void makeMove() {
        // À continuer d'ici...
    }
}
