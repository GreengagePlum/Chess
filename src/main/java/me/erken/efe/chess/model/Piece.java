package me.erken.efe.chess.model;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Piece {
    private final Color color;
    protected List<Coordinates> legalPositions;
    protected List<Coordinates> attackingPositions;
    private boolean kingProtector;
    private Piece kingProtectorCausingPiece;

    public Piece(Color color) {
        this.color = color;
        legalPositions = new LinkedList<>();
        attackingPositions = new LinkedList<>();
        kingProtector = false;
        kingProtectorCausingPiece = null;
    }

    public Color getColor() {
        return color;
    }

    public ListIterator<Coordinates> getLegalPositions() {
        return legalPositions.listIterator();
    }

    public ListIterator<Coordinates> getAttackingPositions() {
        return attackingPositions.listIterator();
    }

    public Piece getKingProtectorCausingPiece() {
        return kingProtectorCausingPiece;
    }

    public void setKingProtectorCausingPiece(Piece p) {
        kingProtectorCausingPiece = p;
    }

    public void clearKingProtectorCausingPiece() {
        kingProtectorCausingPiece = null;
    }

    public boolean legalPositionsContains(Coordinates coordinates) {
        return legalPositions.contains(coordinates);
    }

    public int legalPositionsCount() {
        return legalPositions.size();
    }

    public boolean isKingProtector() {
        return kingProtector;
    }

    public void setKingProtector(boolean kingProtector) {
        this.kingProtector = kingProtector;
    }

    public boolean coordinateCheck(Coordinates coords) {
        return coords.x < Board.WIDTH && coords.x >= 0 && coords.y < Board.HEIGHT && coords.y >= 0;
    }

    protected abstract boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    protected abstract boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    abstract boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords);

    abstract boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    protected abstract void updateLegalPositions(Coordinates sourceCoords, Board board);

    protected abstract void updateAttackingPositions(Coordinates sourceCoords, Board board);

    protected void updateAllPositions(Coordinates sourceCoords, Board board) {
        // can be optimized
        updateLegalPositions(sourceCoords, board);
        updateAttackingPositions(sourceCoords, board);
    }

    protected abstract void setKingProtectorsInPath(Coordinates sourceCoords, Board board);

    protected void setOppositeKingToCheck(Board board) {
        Color oppositeColor = (this.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK;
        if (legalPositions.contains(board.findPiece(board.getKing(oppositeColor)))) {
            board.getKing(oppositeColor).setInCheck(true);
            board.getKing(oppositeColor).addAttackingPiece(this);
        }
    }

    protected abstract boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board);
}
