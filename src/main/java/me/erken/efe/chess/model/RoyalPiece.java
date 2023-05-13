package me.erken.efe.chess.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract non-sealed class RoyalPiece extends Piece {
    private final List<Piece> attackingPieces;
    private boolean inCheck;

    public RoyalPiece(Color color) {
        super(color);
        inCheck = false;
        attackingPieces = new LinkedList<>();
    }

    public boolean isInCheck() {
        return inCheck;
    }

    protected void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    protected void addAttackingPiece(Piece piece) {
        attackingPieces.add(piece);
    }

    protected void clearAttackingPieces() {
        attackingPieces.clear();
    }

    public ListIterator<Piece> getAttackingPieces() {
        return Collections.unmodifiableList(attackingPieces).listIterator();
    }

}
