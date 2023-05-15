package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public final class RegularMove extends Move {
    public final Square source, destination;
    public final Piece takenPiece;

    public RegularMove(Player player, Square source, Square destination) {
        super(player);
        this.source = source;
        this.destination = destination;
        this.takenPiece = destination.getPiece();
    }

    Square getSource() {
        return source;
    }

    Square getDestination() {
        return destination;
    }

    @Override
    public List<Coordinates> concernedCoords(Board board) {
        List<Coordinates> result = new LinkedList<>();
        result.add(board.findSquare(source));
        result.add(board.findSquare(destination));
        return result;
    }

    @Override
    protected void doMove(Board board) throws IllegalMoveException {
        Piece origin = this.source.getPiece();
        if (!isExecuted()) {
            if (origin.legalPositionsContains(board.findSquare(destination))) {
                this.destination.setPiece(this.source.getPiece());
                this.source.setPiece(null);
                if (origin instanceof Pawn) {
                    ((Pawn) origin).consumeFirstMove();
                } else if (origin instanceof King) {
                    ((King) origin).setMoved(true);
                } else if (origin instanceof Rook) {
                    ((Rook) origin).setMoved(true);
                }
                setExecuted(true);
            } else {
                throw new IllegalMoveException();
            }
        }
    }

    @Override
    protected void undoMove() {
        throw new UnsupportedOperationException();
    }

    void undoMove(Stack<Move> past) {
        if (isExecuted()) {
            Piece origin = this.destination.getPiece();
            if (origin instanceof Pawn || origin instanceof Rook || origin instanceof King) {
                boolean resetFirstMove = true;
                for (Move m :
                        past) {
                    if (m.getOriginPiece() == origin) {
                        resetFirstMove = false;
                        break;
                    }
                }
                if (resetFirstMove) {
                    if (origin instanceof Pawn) {
                        ((Pawn) origin).resetFirstMove();
                    } else if (origin instanceof Rook) {
                        ((Rook) origin).setMoved(false);
                    } else {
                        ((King) origin).setMoved(false);
                    }
                }
            }
            this.source.setPiece(this.destination.getPiece());
            this.destination.setPiece(this.takenPiece);
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
