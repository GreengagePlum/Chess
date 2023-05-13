package me.erken.efe.chess.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Pawn extends RegularPiece {

    private boolean firstMove;

    public Pawn(Color color) {
        super(color);
        firstMove = true;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void consumeFirstMove() {
        firstMove = false;
    }

    private boolean isDiagMove(Coordinates sourceCoords, Coordinates destinationCoords) {
        return Math.abs(destinationCoords.x - sourceCoords.x) == 1 && Math.abs(destinationCoords.y - sourceCoords.y) == 1;
    }

    private boolean pathCheckStraight(Coordinates sourceCoords, Coordinates destinationCoords) {
        boolean preCheck;
        int diffY = destinationCoords.y - sourceCoords.y;
        int diffX = destinationCoords.x - sourceCoords.x;
        if (this.getColor() == Color.WHITE) {
            preCheck = 0 > diffY && diffY >= ((firstMove) ? -2 : -1) && diffX == 0;
        } else {
            preCheck = 0 < diffY && diffY <= ((firstMove) ? 2 : 1) && diffX == 0;
        }
        return preCheck;
    }

    private boolean pathCheckDiag(Coordinates sourceCoords, Coordinates destinationCoords) {
        boolean preCheck;
        int diffY = destinationCoords.y - sourceCoords.y;
        int diffX = destinationCoords.x - sourceCoords.x;
        if (this.getColor() == Color.WHITE) {
            preCheck = diffY == -1 && Math.abs(diffX) == 1;
        } else {
            preCheck = diffY == 1 && Math.abs(diffX) == 1;
        }
        return preCheck;
    }

    @Override
    protected boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords) {
        return pathCheckStraight(sourceCoords, destinationCoords) || pathCheckDiag(sourceCoords, destinationCoords);
    }

    @Override
    protected boolean destinationPieceCheck(Coordinates destinationCoords, Board board) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    private boolean destinationPieceCheck(Coordinates destinationCoords, Board board, boolean diagMove) {
        Piece destPiece = board.getSquare(destinationCoords).getPiece();
        if (diagMove) {
            return destPiece != null && destPiece.getColor() != this.getColor();
        }
        return destPiece == null;
    }

    @Override
    protected boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        if (!firstMove && destinationCoords.x != sourceCoords.x) {
            return true;
        }
        int yEvolution = ((destinationCoords.y - sourceCoords.y < 0) ? -1 : 1);
        int y = sourceCoords.y + yEvolution;
        while (y != destinationCoords.y) {
            Piece p = board.getSquare(new Coordinates(sourceCoords.x, y)).getPiece();
            if (p != null) {
                return false;
            }
            y += yEvolution;
        }
        return true;
    }

    @Override
    protected boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return false;
    }

    boolean isLegalPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board, MoveHistory history) {
        return coordinateCheck(destinationCoords)
                && destinationPieceCheck(destinationCoords, board, isDiagMove(sourceCoords, destinationCoords))
                && (pathCheck(sourceCoords, destinationCoords) && obstructionCheck(sourceCoords, destinationCoords, board));
    }

    @Override
    protected boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords) && pathCheckDiag(sourceCoords, destinationCoords);
    }

    private List<Coordinates> traversePath(Coordinates sourceCoords, Board board, Predicate3<Coordinates, Coordinates, Board> checker) {
        List<Coordinates> result = new LinkedList<>();
        int yEvolution = (this.getColor() == Color.WHITE) ? -1 : 1;
        Coordinates check = new Coordinates(sourceCoords.x - 1, sourceCoords.y + yEvolution);
        if (checker.accept(sourceCoords, check, board)) {
            result.add(check);
        }
        check = new Coordinates(sourceCoords.x + 1, sourceCoords.y + yEvolution);
        if (checker.accept(sourceCoords, check, board)) {
            result.add(check);
        }
        check = new Coordinates(sourceCoords.x, sourceCoords.y + yEvolution);
        if (checker.accept(sourceCoords, check, board)) {
            result.add(check);
        }
        check = new Coordinates(sourceCoords.x, sourceCoords.y + 2 * yEvolution);
        // can be optimized for firstMove
        if (checker.accept(sourceCoords, check, board)) {
            result.add(check);
        }
        return result;
    }

    @Override
    public void updateLegalPositions(Coordinates sourceCoords, Board board) {
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
    public void updateAttackingPositions(Coordinates sourceCoords, Board board) {
        attackingPositions.clear();
        attackingPositions.addAll(traversePath(sourceCoords, board, this::isAttackingPosition));
    }

    @Override
    public void updateAllPositions(Coordinates sourceCoords, Board board) {
    }

    public void updateAllPositions(Coordinates sourceCoords, Board board, MoveHistory history) {
        // can be optimized
        updateLegalPositions(sourceCoords, board, history);
        updateAttackingPositions(sourceCoords, board);
    }

    @Override
    protected void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {

    }

    @Override
    protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
        return false;
    }
}
