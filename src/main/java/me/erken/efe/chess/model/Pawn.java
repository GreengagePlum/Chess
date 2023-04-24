package me.erken.efe.chess.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Pawn extends Piece {

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

    private boolean pathCheckStraight(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        boolean preCheck;
        int diffY = destinationCoords.y - sourceCoords.y;
        int diffX = destinationCoords.x - sourceCoords.x;
        if (this.getColor() == Color.WHITE) {
            preCheck = 0 > diffY && diffY >= ((firstMove) ? -2 : -1) && diffX == 0;
        } else {
            preCheck = 0 < diffY && diffY <= ((firstMove) ? 2 : 1) && diffX == 0;
        }
        if (preCheck) {
            preCheck = destinationPieceCheck(destinationCoords, board, false);
        }
        return preCheck;
    }

    private boolean pathCheckDiag(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        boolean preCheck;
        int diffY = destinationCoords.y - sourceCoords.y;
        int diffX = destinationCoords.x - sourceCoords.x;
        if (this.getColor() == Color.WHITE) {
            preCheck = diffY == -1 && Math.abs(diffX) == 1;
        } else {
            preCheck = diffY == 1 && Math.abs(diffX) == 1;
        }
        if (preCheck) {
            preCheck = destinationPieceCheck(destinationCoords, board, true);
        }
        return preCheck;
    }

    private boolean pathCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return pathCheckStraight(sourceCoords, destinationCoords, board) || pathCheckDiag(sourceCoords, destinationCoords, board);
    }

    private boolean destinationPieceCheck(Coordinates destinationCoords, Board board, boolean diagMove) {
        Piece destPiece = board.getSquare(destinationCoords).getPiece();
        if (diagMove) {
            return destPiece != null && destPiece.getColor() != this.getColor();
        }
        return destPiece == null;
    }

    private boolean obstructionCheck(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
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
        return coordinateCheck(destinationCoords)
                && (pathCheck(sourceCoords, destinationCoords, board) && obstructionCheck(sourceCoords, destinationCoords, board));
    }

    @Override
    protected boolean isAttackingPosition(Coordinates sourceCoords, Coordinates destinationCoords, Board board) {
        return coordinateCheck(destinationCoords) && pathCheckDiag(sourceCoords, destinationCoords, board);
    }

    private List<Coordinates> traversePath(Coordinates sourceCoords, Board board, Predicate3<Coordinates, Coordinates, Board> checker) {
        List<Coordinates> result = new LinkedList<>();
        int yEvolution = (this.getColor() == Color.WHITE) ? -1 : 1;
        if (checker.accept(sourceCoords, new Coordinates(sourceCoords.x - 1, sourceCoords.y + yEvolution), board)) {
            result.add(new Coordinates(sourceCoords.x - 1, sourceCoords.y - 1));
        }
        if (checker.accept(sourceCoords, new Coordinates(sourceCoords.x + 1, sourceCoords.y + yEvolution), board)) {
            result.add(new Coordinates(sourceCoords.x + 1, sourceCoords.y - 1));
        }
        if (checker.accept(sourceCoords, new Coordinates(sourceCoords.x, sourceCoords.y + yEvolution), board)) {
            result.add(new Coordinates(sourceCoords.x, sourceCoords.y - 1));
        }
        // can be optimized for firstMove
        if (checker.accept(sourceCoords, new Coordinates(sourceCoords.x, sourceCoords.y + 2 * yEvolution), board)) {
            result.add(new Coordinates(sourceCoords.x, sourceCoords.y - 2));
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
                int attackersCount = k.getAttackingPiecesCount();
                for (ListIterator<Piece> it = k.getAttackingPieces(); it.hasNext(); ) {
                    Piece p = it.next();
                    // can be optimized for jumping pieces (like Knights)
                    if (!p.legalPositionsContains(pos) && !(pos.equals(board.findPiece(p)) && attackersCount == 1)) {
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
    }

    @Override
    protected void setKingProtectorsInPath(Coordinates sourceCoords, Board board) {

    }

    @Override
    protected boolean posInPathLeadingToKing(Coordinates sourceCoords, Coordinates toTest, Board board) {
        return false;
    }
}
