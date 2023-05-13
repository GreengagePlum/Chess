package me.erken.efe.chess.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Knight extends RegularPiece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    protected boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords) {
        return (destinationCoords.y == sourceCoords.y - 2 && destinationCoords.x == sourceCoords.x - 1)
                || (destinationCoords.y == sourceCoords.y - 1 && destinationCoords.x == sourceCoords.x - 2)
                || (destinationCoords.y == sourceCoords.y + 1 && destinationCoords.x == sourceCoords.x - 2)
                || (destinationCoords.y == sourceCoords.y + 2 && destinationCoords.x == sourceCoords.x - 1)
                || (destinationCoords.y == sourceCoords.y + 2 && destinationCoords.x == sourceCoords.x + 1)
                || (destinationCoords.y == sourceCoords.y + 1 && destinationCoords.x == sourceCoords.x + 2)
                || (destinationCoords.y == sourceCoords.y - 1 && destinationCoords.x == sourceCoords.x + 2)
                || (destinationCoords.y == sourceCoords.y - 2 && destinationCoords.x == sourceCoords.x + 1);
    }

    @Override
    protected boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return true;
    }

    @Override
    protected boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords)
                && destinationPieceCheck(destinationCoords, board)
                && pathCheck(sourceCoords, destinationCoords);
    }

    @Override
    protected boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords)
                && pathCheck(sourceCoords, destinationCoords);
    }

    private List<Coordinates> traversePath(Coordinates sourceCoords, Board board, Predicate3<Coordinates, Coordinates, Board> checker) {
        List<Coordinates> result = new LinkedList<>();
        Coordinates tmp = new Coordinates(sourceCoords.x - 1, sourceCoords.y - 2);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        tmp = new Coordinates(sourceCoords.x - 2, sourceCoords.y - 1);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        tmp = new Coordinates(sourceCoords.x - 2, sourceCoords.y + 1);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        tmp = new Coordinates(sourceCoords.x - 1, sourceCoords.y + 2);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        tmp = new Coordinates(sourceCoords.x + 1, sourceCoords.y + 2);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        tmp = new Coordinates(sourceCoords.x + 2, sourceCoords.y + 1);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        tmp = new Coordinates(sourceCoords.x + 2, sourceCoords.y - 1);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        tmp = new Coordinates(sourceCoords.x + 1, sourceCoords.y - 2);
        if (checker.accept(sourceCoords, tmp, board)) {
            result.add(tmp);
        }
        return result;
    }

    @Override
    protected void updateLegalPositions(Coordinates sourceCoords, Board board) {
        legalPositions.clear();
        King k = board.getKing(this.getColor());
        if (k.isInCheck() && this.isRoyalProtector()) {
            return;
        }
        List<Coordinates> possibilities = traversePath(sourceCoords, board, this::isLegalPosition);
        if (k.isInCheck()) {
            for (Iterator<Coordinates> iterator = possibilities.iterator(); iterator.hasNext(); ) {
                Coordinates pos = iterator.next();
                for (ListIterator<Piece> it = k.getAttackingPieces(); it.hasNext(); ) {
                    Piece p = it.next();
                    // can be optimized for jumping pieces (like Knights)
                    if ((!p.legalPositionsContains(pos) || !p.posInPathLeadingToKing(board.findPiece(p), pos, board)) && !pos.equals(board.findPiece(p))) {
                        iterator.remove();
                    }
                }
            }
        } else if (this.isRoyalProtector()) {
            Coordinates threat = board.findPiece(this.getRoyalProtectorCausingPiece());
            possibilities.removeIf(pos -> (!pos.equals(threat) && !(this.getRoyalProtectorCausingPiece().posInPathLeadingToKing(threat, pos, board))));
        }
        legalPositions.addAll(possibilities);
        setOppositeKingToCheck(board);
    }

    @Override
    protected void updateAttackingPositions(Coordinates sourceCoords, Board board) {
        attackingPositions.clear();
        attackingPositions.addAll(traversePath(sourceCoords, board, this::isAttackingPosition));
    }

    @Override
    protected void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {
    }

    @Override
    protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
        return false;
    }
}
