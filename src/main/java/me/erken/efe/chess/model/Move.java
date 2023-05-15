package me.erken.efe.chess.model;

import java.util.List;

public abstract class Move {
    public final Player player;
    private boolean executed;

    public Move(Player player) {
        this.player = player;
        executed = false;
    }

    public boolean isExecuted() {
        return executed;
    }

    public abstract List<Coordinates> concernedCoords(Board board);

    protected void setExecuted(boolean executed) {
        this.executed = executed;
    }

    protected abstract void doMove(Board board) throws IllegalMoveException;

    protected abstract void undoMove();

    protected abstract Piece getOriginPiece();
}
