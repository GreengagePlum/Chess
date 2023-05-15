package me.erken.efe.chess.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public sealed abstract class Piece permits RegularPiece, RoyalPiece {
    private final Color color;
    protected List<Coordinates> legalPositions;
    protected List<Coordinates> attackingPositions;
    private boolean royalProtector;
    private RegularPiece royalProtectorCausingPiece;

    public Piece(Color color) {
        this.color = color;
        legalPositions = new LinkedList<>();
        attackingPositions = new LinkedList<>();
        royalProtector = false;
        royalProtectorCausingPiece = null;
    }

    public Color getColor() {
        return color;
    }

    public ListIterator<Coordinates> getLegalPositions() {
        return Collections.unmodifiableList(legalPositions).listIterator();
    }

    public ListIterator<Coordinates> getAttackingPositions() {
        return Collections.unmodifiableList(attackingPositions).listIterator();
    }

    public Piece getRoyalProtectorCausingPiece() {
        return royalProtectorCausingPiece;
    }

    protected void setRoyalProtectorCausingPiece(RegularPiece p) {
        royalProtectorCausingPiece = p;
    }

    protected void clearKingProtectorCausingPiece() {
        royalProtectorCausingPiece = null;
    }

    public boolean legalPositionsContains(Coordinates coordinates) {
        return legalPositions.contains(coordinates);
    }

    public int legalPositionsCount() {
        return legalPositions.size();
    }

    public boolean isRoyalProtector() {
        return royalProtector;
    }

    protected void setRoyalProtector(boolean royalProtector) {
        this.royalProtector = royalProtector;
    }

    protected boolean coordinateCheck(Coordinates coords) {
        return coords.x < Board.WIDTH && coords.x >= 0 && coords.y < Board.HEIGHT && coords.y >= 0;
    }

    protected abstract boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    protected abstract boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    protected abstract boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords);

    protected boolean destinationPieceCheck(Coordinates destinationCoords, Board board) {
        Piece destPiece = board.getSquare(destinationCoords).getPiece();
        return destPiece == null || destPiece.getColor() != this.getColor();
    }

    protected abstract boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    protected abstract void updateLegalPositions(Coordinates sourceCoords, Board board);

    protected abstract void updateAttackingPositions(Coordinates sourceCoords, Board board);

    public void updateAllPositions(Coordinates sourceCoords, Board board) {
        // can be optimized
        updateLegalPositions(sourceCoords, board);
        updateAttackingPositions(sourceCoords, board);
    }

    protected abstract boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board);
}
