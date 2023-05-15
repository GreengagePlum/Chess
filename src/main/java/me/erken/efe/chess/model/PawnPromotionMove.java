package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;

public final class PawnPromotionMove extends Move {

    public final Square source, destination;
    public final Piece oldSelf, newSelf;
    public final Piece takenPiece;

    public PawnPromotionMove(Player player, Square source, Square destination, String rank) throws PawnPromotionException {
        super(player);
        this.source = source;
        this.destination = destination;
        this.oldSelf = source.getPiece();
        if (rank == null) {
            throw new PawnPromotionException();
        } else if (rank.equals("Queen")) {
            this.newSelf = new Queen(source.getPiece().getColor());
        } else if (rank.equals("Rook")) {
            this.newSelf = new Rook(source.getPiece().getColor());
        } else if (rank.equals("Bishop")) {
            this.newSelf = new Bishop(source.getPiece().getColor());
        } else if (rank.equals("Knight")) {
            this.newSelf = new Knight(source.getPiece().getColor());
        } else {
            throw new PawnPromotionException();
        }
        this.takenPiece = this.destination.getPiece();
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
                this.destination.setPiece(this.newSelf);
                this.source.setPiece(null);
                setExecuted(true);
            } else {
                throw new IllegalMoveException();
            }
        }
    }

    @Override
    protected void undoMove() {
        if (isExecuted()) {
            this.source.setPiece(this.oldSelf);
            this.destination.setPiece(this.takenPiece);
            setExecuted(false);
        }
    }

    @Override
    protected Piece getOriginPiece() {
        return oldSelf;
    }
}
