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

    public boolean isKingProtector() {
        return kingProtector;
    }

    public void setKingProtector(boolean kingProtector) {
        this.kingProtector = kingProtector;
    }

    public boolean coordinateCheck(Coordinates coords) {
        return coords.x < Board.WIDTH && coords.x >= 0 && coords.y < Board.HEIGHT && coords.y >= 0;
    }

    protected abstract boolean isValidMove(Coordinates sourceCoords, Coordinates destinationCoords, Board board);

    protected abstract Coordinates[] movablePositions(Coordinates sourceCoords, Board board);

}
