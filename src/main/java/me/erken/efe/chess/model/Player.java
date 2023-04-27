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

    private void clearSelection() {
        selection = null;
    }

    public Square getSelection() {
        return selection;
    }

    public void makeSelection(Square square, Board board) {
        board.clearStateSquares();
        this.clearSelection();
        Piece p = square.getPiece();
        if (p != null && p.getColor() == color) {
            board.selectSquare(square);
            this.setSelection(square);
        }
    }

    public void makeMove(Square destination, MoveHistory history, Board board) throws IllegalMoveException {
        if (selection == null) {
            return;
        }
        Move move = new Move(this, selection, destination);
        try {
            move.doMove(board);
            history.addMove(move);
        } finally {
            this.clearSelection();
            board.clearStateSquares();
        }
    }
}
