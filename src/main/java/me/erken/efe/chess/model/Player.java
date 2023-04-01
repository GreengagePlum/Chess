package me.erken.efe.chess.model;

public class Player {
    private static int iDCounter = 0;
    private final Color color;
    private final int id;

    public Player(Color color) {
        this.color = color;
        id = iDCounter;
        iDCounter++;
    }

    public Color getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public void makeSelection(Square square, Board board) {
        Piece p = square.getPiece();
        if (p == null || p.getColor() != color) {
            board.clearSquares();
        } else {
            board.selectSquare(square);
        }
    }

    public void makeMove() {
        // À continuer d'ici...
    }
}
