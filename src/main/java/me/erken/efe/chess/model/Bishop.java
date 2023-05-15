package me.erken.efe.chess.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Bishop extends RegularPiece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    protected boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords) {
        return sourceCoords != destinationCoords && Math.abs(destinationCoords.x - sourceCoords.x) == Math.abs(destinationCoords.y - sourceCoords.y);
    }

    @Override
    protected boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        int yEvolution = ((destinationCoords.y - sourceCoords.y < 0) ? -1 : 1);
        int xEvolution = ((destinationCoords.x - sourceCoords.x < 0) ? -1 : 1);
        int x = sourceCoords.x + xEvolution;
        int y = sourceCoords.y + yEvolution;
        while (x != destinationCoords.x && y != destinationCoords.y) {
            Piece p = board.getSquare(new Coordinates(x, y)).getPiece();
            if (p != null) {
                return false;
            }
            x += xEvolution;
            y += yEvolution;
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
        int x = sourceCoords.x - 1;
        int y = sourceCoords.y - 1;
        while (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
            x--;
            y--;
        }
        x = sourceCoords.x - 1;
        y = sourceCoords.y + 1;
        while (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
            x--;
            y++;
        }
        x = sourceCoords.x + 1;
        y = sourceCoords.y + 1;
        while (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
            x++;
            y++;
        }
        x = sourceCoords.x + 1;
        y = sourceCoords.y - 1;
        while (checker.accept(sourceCoords, new Coordinates(x, y), board)) {
            result.add(new Coordinates(x, y));
            x++;
            y--;
        }
        return result;
    }

    @Override
    protected void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {
        for (Coordinates pos :
                attackingPositions) {
            Piece p = board.getSquare(pos).getPiece();
            if (p != null && p.getColor() != getColor()) {
                List<Coordinates> secondJump = traversePath(pos, board, this::isAttackingPosition);
                for (Coordinates pos2 :
                        secondJump) {
                    Piece p2 = board.getSquare(pos2).getPiece();
                    if (p2 instanceof King && p2.getColor() != getColor() && pathCheck(sourceCoords, pos2)) {
                        p.setRoyalProtector(true);
                        p.setRoyalProtectorCausingPiece(this);
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
        int yEvolution = (kingPos.y - sourceCoords.y < 0) ? -1 : 1;
        int xEvolution = (kingPos.x - sourceCoords.x < 0) ? -1 : 1;
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
                        break;
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
        setKingProtectorsInPath(sourceCoords, board);
    }
}
