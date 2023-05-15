package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;

public final class EnPassantMove extends Move {

    public final Square source, destination, victim;
    public final Piece takenPiece;

    public EnPassantMove(Player player, Square source, Square destination, Square victim) {
        super(player);
        this.source = source;
        this.destination = destination;
        this.victim = victim;
        this.takenPiece = victim.getPiece();
    }

    @Override
    public List<Coordinates> concernedCoords(Board board) {
        List<Coordinates> result = new LinkedList<>();
        result.add(board.findSquare(source));
        result.add(board.findSquare(destination));
        result.add(board.findSquare(victim));
        return result;
    }

    @Override
    protected void doMove(Board board) throws IllegalMoveException {
        Piece origin = this.source.getPiece();
        if (!isExecuted()) {
            if (origin.legalPositionsContains(board.findSquare(destination))) {
                this.destination.setPiece(this.source.getPiece());
                this.source.setPiece(null);
                this.victim.setPiece(null);
                setExecuted(true);
            } else {
                throw new IllegalMoveException();
            }
        }
    }

    @Override
    protected void undoMove() {
        if (isExecuted()) {
            this.source.setPiece(this.destination.getPiece());
            this.destination.setPiece(null);
            this.victim.setPiece(this.takenPiece);
            setExecuted(false);
        }
    }

    @Override
    protected Piece getOriginPiece() {
        if (isExecuted()) {
            return destination.getPiece();
        } else {
            return source.getPiece();
        }
    }
}
