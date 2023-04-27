package me.erken.efe.chess.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    @Override
    boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords) {
        return (destinationCoords.x == sourceCoords.x && destinationCoords.y != sourceCoords.y)
                || (destinationCoords.x != sourceCoords.x && destinationCoords.y == sourceCoords.y);
    }

    private boolean destinationPieceCheck(Coordinates destinationCoords, Board board) {
        Piece destPiece = board.getSquare(destinationCoords).getPiece();
        return destPiece == null || destPiece.getColor() != this.getColor();
    }

    @Override
    boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        int xEvolution, yEvolution;
        if (destinationCoords.x == sourceCoords.x) {
            xEvolution = 0;
        } else {
            xEvolution = ((destinationCoords.x - sourceCoords.x < 0) ? -1 : 1);
        }
        if (destinationCoords.y == sourceCoords.y) {
            yEvolution = 0;
        } else {
            yEvolution = ((destinationCoords.y - sourceCoords.y < 0) ? -1 : 1);
        }
        Coordinates iter = new Coordinates(sourceCoords.x + xEvolution, sourceCoords.y + yEvolution);
        while (!iter.equals(destinationCoords)) {
            if (board.getSquare(iter).getPiece() != null) {
                return false;
            }
            iter.x += xEvolution;
            iter.y += yEvolution;
        }
        return true;
    }

    @Override
    protected boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords)
                && destinationPieceCheck(destinationCoords, board)
                && (pathCheck(sourceCoords, destinationCoords) && obstructionCheck(sourceCoords, destinationCoords, board));
    }

    @Override
    protected boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords)
                && (pathCheck(sourceCoords, destinationCoords) && obstructionCheck(sourceCoords, destinationCoords, board));
    }

    private List<Coordinates> traversePath(Coordinates sourceCoords, Board board, Predicate3<Coordinates, Coordinates, Board> checker) {
        List<Coordinates> result = new LinkedList<>();
        for (int y = 0; y < Board.HEIGHT; y++) {
            if (y != sourceCoords.y) {
                Coordinates tmp = new Coordinates(sourceCoords.x, y);
                if (checker.accept(sourceCoords, tmp, board)) {
                    result.add(tmp);
                }
            }
        }
        for (int x = 0; x < Board.WIDTH; x++) {
            if (x != sourceCoords.x) {
                Coordinates tmp = new Coordinates(x, sourceCoords.y);
                if (checker.accept(sourceCoords, tmp, board)) {
                    result.add(tmp);
                }
            }
        }
        return result;
    }

    @Override
    public void updateLegalPositions(Coordinates sourceCoords, Board board) {
        legalPositions.clear();
        King k = board.getKing(this.getColor());
        if (k.isInCheck() && this.isKingProtector()) {
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
        } else if (this.isKingProtector()) {
            Coordinates threat = board.findPiece(this.getKingProtectorCausingPiece());
            possibilities.removeIf(pos -> (!pos.equals(threat) && !(this.getKingProtectorCausingPiece().posInPathLeadingToKing(threat, pos, board))));
        }
        legalPositions.addAll(possibilities);
        setOppositeKingToCheck(board);
    }

    @Override
    public void updateAttackingPositions(Coordinates sourceCoords, Board board) {
        attackingPositions.clear();
        attackingPositions.addAll(traversePath(sourceCoords, board, this::isAttackingPosition));
        setKingProtectorsInPath(sourceCoords, board);
    }

    @Override
    public void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {
        for (Coordinates pos :
                attackingPositions) {
            Piece p = board.getSquare(pos).getPiece();
            if (p != null && p.getColor() != getColor()) {
                List<Coordinates> secondJump = traversePath(pos, board, this::isAttackingPosition);
                for (Coordinates pos2 :
                        secondJump) {
                    Piece p2 = board.getSquare(pos2).getPiece();
                    if (p2 instanceof King && p2.getColor() != getColor() && pathCheck(sourceCoords, pos2)) {
                        p.setKingProtector(true);
                        p.setKingProtectorCausingPiece(this);
                    }
                }
            }
        }
    }

    @Override
    protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
        Coordinates kingPos = board.findPiece(board.getKing((this.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK));
        // redundant condition (toTest == kingPos), tested also in while loop below
        if (toTest.equals(sourceCoords) || toTest.equals(kingPos)) {
            return false;
        }
        int xEvolution, yEvolution;
        if (kingPos.x == sourceCoords.x) {
            xEvolution = 0;
        } else {
            xEvolution = ((kingPos.x - sourceCoords.x < 0) ? -1 : 1);
        }
        if (kingPos.y == sourceCoords.y) {
            yEvolution = 0;
        } else {
            yEvolution = ((kingPos.x - sourceCoords.x < 0) ? -1 : 1);
        }
        Coordinates tmp = new Coordinates(sourceCoords.x + xEvolution, sourceCoords.y + yEvolution);
        while (!tmp.equals(kingPos)) {
            if (tmp.equals(toTest)) {
                return true;
            }
            tmp.x += xEvolution;
            tmp.y += yEvolution;
        }
        return false;
    }
}
