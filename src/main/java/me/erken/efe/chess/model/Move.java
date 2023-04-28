package me.erken.efe.chess.model;

public class Move {
    public final Player player;
    public final Square source, destination;
    public final Piece takenPiece;
    private boolean executed;

    public Move(Player player, Square source, Square destination) {
        this.player = player;
        this.source = source;
        this.destination = destination;
        this.takenPiece = destination.getPiece();
        executed = false;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void doMove(Board board) throws IllegalMoveException {
        Piece origin = this.source.getPiece();
        if (!executed) {
            if (origin.legalPositionsContains(board.findSquare(destination))) {
                this.destination.setPiece(this.source.getPiece());
                this.source.setPiece(null);
                if (origin instanceof Pawn) {
                    ((Pawn) origin).consumeFirstMove();
                }
                executed = true;
            } else {
                throw new IllegalMoveException();
            }
        }
    }

    public void undoMove() {
        if (executed) {
            this.source.setPiece(this.destination.getPiece());
            this.destination.setPiece(this.takenPiece);
            executed = false;
        }
    }
}
