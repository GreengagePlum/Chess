package me.erken.efe.chess.model;

public class Player {
    private final Color color;
    private Square selection;

    public Player(Color color) {
        this.color = color;
        selection = null;
    }

    public Color getColor() {
        return color;
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
            board.clearStateSquares();
            this.unsetSelection();
        } else {
            board.selectSquare(square);
            this.setSelection(square);
        }
    }

    public void makeMove(Square destination, MoveHistory history, Board board) {
        Move move = new Move(this, selection, destination);
        try {
            move.executeMove(board);
            history.addMove(move);
        } catch (IllegalMoveException me) {
            System.out.println("Coup impossible");
        } finally {
            this.unsetSelection();
            board.clearStateSquares();
        }
    }
}
