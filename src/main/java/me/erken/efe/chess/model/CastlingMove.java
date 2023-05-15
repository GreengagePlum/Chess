package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;

public final class CastlingMove extends Move {

    public final Square sourceKing, destinationKing;
    public final Square sourceRook, destinationRook;

    public CastlingMove(Player player, Square sourceKing, Square destinationKing, Square sourceRook, Square destinationRook) {
        super(player);
        this.sourceKing = sourceKing;
        this.destinationKing = destinationKing;
        this.sourceRook = sourceRook;
        this.destinationRook = destinationRook;
    }

    @Override
    public List<Coordinates> concernedCoords(Board board) {
        List<Coordinates> result = new LinkedList<>();
        result.add(board.findSquare(sourceKing));
        result.add(board.findSquare(destinationKing));
        result.add(board.findSquare(sourceRook));
        result.add(board.findSquare(destinationRook));
        return result;
    }

    @Override
    protected void doMove(Board board) throws IllegalMoveException {
        King king = (King) this.sourceKing.getPiece();
        Rook rook = (Rook) this.sourceRook.getPiece();
        if (!isExecuted()) {
            if (king.legalPositionsContains(board.findSquare(destinationKing))) {
                this.destinationKing.setPiece(this.sourceKing.getPiece());
                this.sourceKing.setPiece(null);
                this.destinationRook.setPiece(this.sourceRook.getPiece());
                this.sourceRook.setPiece(null);
                king.setMoved(true);
                rook.setMoved(true);
                setExecuted(true);
            } else {
                throw new IllegalMoveException();
            }
        }
    }

    @Override
    protected void undoMove() {
        if (isExecuted()) {
            King king = (King) this.destinationKing.getPiece();
            Rook rook = (Rook) this.destinationRook.getPiece();
            this.sourceKing.setPiece(this.destinationKing.getPiece());
            this.destinationKing.setPiece(null);
            this.sourceRook.setPiece(this.destinationRook.getPiece());
            this.destinationRook.setPiece(null);
            king.setMoved(false);
            rook.setMoved(false);
            setExecuted(false);
        }
    }

    @Override
    protected Piece getOriginPiece() {
        if (isExecuted()) {
            return destinationKing.getPiece();
        } else {
            return sourceKing.getPiece();
        }
    }
}
